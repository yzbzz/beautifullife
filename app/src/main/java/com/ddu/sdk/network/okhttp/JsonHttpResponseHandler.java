//package com.ddu.sdk.network.okhttp;
//
//import com.google.gson.Gson;
//import com.google.gson.internal.$Gson$Types;
//
//import java.io.IOException;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//
///**
// * Created by yzbzz on 2018/9/3.
// */
//public abstract class JsonHttpResponseHandler<T> extends AsyncHttpResponseHandler {
//
//    private Gson mGson;
//
//    private Type type;
//
//    public JsonHttpResponseHandler() {
//        type = getSuperclassTypeParameter(getClass());
//        mGson = new Gson();
//    }
//
//    private Type getSuperclassTypeParameter(Class<?> subclass) {
//        Type superclass = subclass.getGenericSuperclass();
//        if ((superclass instanceof Class)) {
//            throw new RuntimeException("Missing type parameter.");
//        }
//        ParameterizedType parameterized = (ParameterizedType) superclass;
//        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
//    }
//
//    public final Type getType() {
//        return type;
//    }
//
//    @Override
//    public void onResponse(Response response) {
//        if (response.isSuccessful()) {
//            try {
//                String string = response.body().string();
//                Type type = getType();
//                T t = mGson.fromJson(string, type);
//                // gson.fromJson(response.body().charStream(), type.getClass());
//                onResponse(t);
//            } catch (IOException e) {
//                onError(response.code(), response);
//            }
//        } else {
//            onError(response.code(), response);
//        }
//    }
//
//    public abstract void onResponse(T response);
//
//}
