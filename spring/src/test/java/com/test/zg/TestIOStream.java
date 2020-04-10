package com.test.zg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class TestIOStream {
    OutputStream bOut=new ByteArrayOutputStream();
//    OutputStream bOut2=new ByteArrayOutputStream(int a);
public static void main(String[] args) throws Exception{
    ByteArrayOutputStream bOutput=new ByteArrayOutputStream(12);
    while (bOutput.size()!=10){
        bOutput.write(System.in.read());
    }
    byte[] bytes=bOutput.toByteArray();
    System.out.println("输入内容:");
    for(int x=0;x<bytes.length;x++){
        System.out.println((byte) bytes[x]+" ");
    }
    System.out.println("  ");
    int c;
    ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
    System.out.println("将字符转换为大写");
    for (int y=0;y<1;y++){
        while ((c=byteArrayInputStream.read())!= -1){
            System.out.println(Character.toTitleCase((char)c));
        }
        byteArrayInputStream.reset();
    }
}
}
