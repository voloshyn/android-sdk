package com.mobilecoin.lib.network.services.grpc;

import androidx.annotation.NonNull;

import com.mobilecoin.lib.exceptions.NetworkException;
import com.mobilecoin.lib.network.NetworkResult;
import com.mobilecoin.lib.network.grpc.AuthInterceptor;
import com.mobilecoin.lib.network.grpc.CookieInterceptor;
import com.mobilecoin.lib.network.grpc.GRPCStatusResponse;
import com.mobilecoin.lib.network.services.FogKeyImageService;

import java.util.concurrent.ExecutorService;

import attest.Attest;
import fog_ledger.FogKeyImageAPIGrpc;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

public class GRPCFogKeyImageService
        extends GRPCService<FogKeyImageAPIGrpc.FogKeyImageAPIBlockingStub>
        implements FogKeyImageService {
    public GRPCFogKeyImageService(@NonNull ManagedChannel managedChannel,
                           @NonNull CookieInterceptor cookieInterceptor,
                           @NonNull AuthInterceptor authInterceptor,
                           @NonNull ExecutorService executorService) {
        super(managedChannel, cookieInterceptor, authInterceptor, executorService);
    }

    @NonNull
    @Override
    FogKeyImageAPIGrpc.FogKeyImageAPIBlockingStub newBlockingStub(
            @NonNull ManagedChannel managedChannel
    ) {
        return FogKeyImageAPIGrpc.newBlockingStub(managedChannel);
    }

    @Override
    public Attest.AuthMessage auth(Attest.AuthMessage authMessage) throws NetworkException {
        try {
            return getApiBlockingStub().auth(authMessage);
        } catch (StatusRuntimeException e) {
            throw new NetworkException(new NetworkResult(new GRPCStatusResponse(e.getStatus())), e);
        }
    }

    @Override
    public Attest.Message checkKeyImages(Attest.Message request) throws NetworkException {
        try {
            return getApiBlockingStub().checkKeyImages(request);
        } catch (StatusRuntimeException e) {
            throw new NetworkException(new NetworkResult(new GRPCStatusResponse(e.getStatus())), e);
        }
    }
}
