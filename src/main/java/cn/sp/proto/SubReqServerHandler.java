package cn.sp.proto;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by 2YSP on 2019/5/4.
 */
public class SubReqServerHandler extends SimpleChannelInboundHandler<Object>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if ("ship".equalsIgnoreCase(req.getUserName())){
            System.out.println("Service accept client subscribe req: [ " + req.toString()+"]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }
    
    private SubscribeRespProto.SubscribeResp resp(int subReqId){
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqId);
        builder.setRespCode(0);
        builder.setDesc("Netty book order successed");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
