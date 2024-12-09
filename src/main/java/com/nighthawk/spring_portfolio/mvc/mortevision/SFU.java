package com.nighthawk.spring_portfolio.mvc.mortevision;

import java.util.Collections;
import dev.onvoid.webrtc.PeerConnectionFactory;
import dev.onvoid.webrtc.PeerConnectionObserver;
import dev.onvoid.webrtc.RTCConfiguration;
import dev.onvoid.webrtc.RTCIceCandidate;
import dev.onvoid.webrtc.RTCIceServer;
import dev.onvoid.webrtc.RTCPeerConnection;
import dev.onvoid.webrtc.RTCSdpType;
import dev.onvoid.webrtc.RTCSessionDescription;
import dev.onvoid.webrtc.media.MediaStream;

public class SFU implements PeerConnectionObserver{

MediaStream broadcaster;

//see front end for complaints

public void consumer(String stp)
{
    if(broadcaster == null)
    {
        return;
    }
    RTCIceServer iceServer = new RTCIceServer();
    iceServer.urls = Collections.singletonList("stun:stun.l.google.com:19302");

    RTCConfiguration config = new RTCConfiguration(); 
    config.iceServers = Collections.singletonList(iceServer);

    PeerConnectionFactory PCF = new PeerConnectionFactory();
    RTCPeerConnection peerConnection = PCF.createPeerConnection(config,this);

    RTCSessionDescription sessionDescription = new RTCSessionDescription(RTCSdpType.OFFER, stp);
    peerConnection.setRemoteDescription(sessionDescription, null); //this is most likley the problem
}

public void broadcast(String stp)
{
    RTCIceServer iceServer = new RTCIceServer();
    iceServer.urls = Collections.singletonList("stun:stun.l.google.com:19302");
    RTCConfiguration config = new RTCConfiguration(); 
    config.iceServers = Collections.singletonList(iceServer);
    PeerConnectionFactory PCF = new PeerConnectionFactory();
    RTCPeerConnection peerConnection = PCF.createPeerConnection(config,this);

    RTCSessionDescription sessionDescription = new RTCSessionDescription(RTCSdpType.OFFER, stp);
    peerConnection.setRemoteDescription(sessionDescription, null);
}


public void onTrackEvent()
{
}

@Override
public void onIceCandidate(RTCIceCandidate candidate) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'onIceCandidate'");
}
}