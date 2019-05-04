package cn.sp.messagepack;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2YSP on 2019/5/2.
 */
public class Test {
    /**
     *  使用messagePack序列化与反序列化
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("五一");
        list.add("无聊");
        list.add("写代码");
        MessagePack messagePack = new MessagePack();
        byte[] raw = messagePack.write(list);
        List<String> stringList = messagePack.read(raw, Templates.tList(Templates.TString));
        System.out.println(stringList.get(0));
        System.out.println(stringList.get(1));
        System.out.println(stringList.get(2));
    }
}
