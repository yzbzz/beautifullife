package com.ddu.icore.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Message
import com.ddu.icore.ICore
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.logic.Actions

class ICoreServiceConnection private constructor() : ServiceConnection, ICoreIPCInterface {

    private var isConnected = false
    private var isBind = false

    private var isUserCancel = false
    private var isKillService = false

    private val isCanRebind: Boolean
        get() = !isUserCancel && !isKillService

    private var mICoreAidlInterface: ICoreAidlInterface? = null
    private var mServiceCallBack: ServiceCallBack? = null

    private object SingletonHolder {
        val instance = ICoreServiceConnection()
    }

    private fun bindService(isFirstBind: Boolean) {
        if (isFirstBind || isCanRebind) {
            isKillService = false
            isUserCancel = false
            synchronized(this) {
                val service = Intent(ICore.context, ICoreService::class.java)
                isBind = ICore.context.bindService(service, this, Context.BIND_AUTO_CREATE)
                if (null != mServiceCallBack) {
                    mServiceCallBack = null
                }
                mServiceCallBack = ServiceCallBack()
            }
        }
    }

    private fun reBindService() {
        if (isCanRebind) {
            synchronized(this) {
                if (isCanRebind) {
                    bindService(false)
                }
            }
        }
    }

    private fun unBindService() {
        isUserCancel = true
        isKillService = true
        release()
        synchronized(this) {
            if (isBind) {
                ICore.context.unbindService(this)
                isBind = false
            }
            isConnected = false
        }
    }

    private fun release() {

        if (null != mICoreAidlInterface) {
            mICoreAidlInterface?.unregisterListener(mServiceCallBack)
            mICoreAidlInterface = null
        }

        if (null != mServiceCallBack) {
            mServiceCallBack = null
        }

        MessageManager.clear()
    }

    private fun killService() {
        isKillService = true
        val message = Message.obtain()
        message.what = Actions.KILL_SERVICE
        sendMsg(message)
    }

    private fun sendMsg(message: Message?) {
        if (null != message) {
            if (isConnected && null != mICoreAidlInterface) {
                try {
                    mICoreAidlInterface?.sendMessage(GodIntent(Actions.CLIENT_MSG_ACTION, message))
                } catch (e: Exception) {
                    e.printStackTrace()
                    enqueueMessageAndReBindService(message)
                }

            } else {
                enqueueMessageAndReBindService(message)
            }
        }
    }

    private fun enqueueMessageAndReBindService(message: Message) {
        if (!isUserCancel) {
            MessageManager.add(message)
            reBindService()
        }
    }

    private fun reSendAllMsg(mICoreAidlInterface: ICoreAidlInterface?) {
        if (isServiceAvailable() && null != mICoreAidlInterface) {
            val concurrentLinkedQueue = MessageManager.getMessages()
            val messageIterator = concurrentLinkedQueue.iterator()
            while (messageIterator.hasNext()) {
                val message = messageIterator.next()
                try {
                    mICoreAidlInterface.sendMessage(GodIntent(Actions.CLIENT_MSG_ACTION, message))
                    messageIterator.remove()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    private val mDeathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            isConnected = false
            if (null == mICoreAidlInterface) {
                return
            }
            mICoreAidlInterface?.unregisterListener(mServiceCallBack)
            mICoreAidlInterface?.asBinder()?.unlinkToDeath(this, DEATH_RECIPIENT_FLAGS)
            mICoreAidlInterface = null
            mServiceCallBack = null
            // Binder死亡，重新绑定服务
            reBindService()
        }
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        isConnected = true
        mICoreAidlInterface = ICoreAidlInterface.Stub.asInterface(service)
        try {
            mICoreAidlInterface?.registerListener(mServiceCallBack)
            if (MessageManager.isNotEmpty()) {
                reSendAllMsg(mICoreAidlInterface)
            }
            mICoreAidlInterface?.asBinder()?.linkToDeath(mDeathRecipient, DEATH_RECIPIENT_FLAGS)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            ObserverManager.notify(Actions.SERVICE_CONNECTED_ACTION)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        isConnected = false
        reBindService()
    }

    override fun onBindingDied(name: ComponentName?) {
        isConnected = false
    }

    override fun onNullBinding(name: ComponentName?) {
        isConnected = false
    }

    private class ServiceCallBack : ICoreAidlCallBackInterface.Stub() {

        override fun callback(godIntent: GodIntent?) {
            godIntent?.let {
                ObserverManager.notify(godIntent)
            }
        }
    }

    override fun bindICoreService() {
        instance.bindService(true)
    }

    override fun unBindICoreService() {
        instance.unBindService()
    }

    override fun sendMessage(message: Message?) {
        message?.let {
            instance.sendMsg(it)
        }
    }

    override fun isServiceAvailable(): Boolean {
        return instance.isBind && instance.isConnected
    }

    override fun killICoreService() {
        instance.killService()
    }

    companion object {

        private const val DEATH_RECIPIENT_FLAGS = 0

        val instance: ICoreServiceConnection
            get() = SingletonHolder.instance
    }

}
