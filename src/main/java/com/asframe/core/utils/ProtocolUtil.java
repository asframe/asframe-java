package com.asframe.core.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
//import org.msgpack.MessagePack;

/**
 * Created by linchm on 2016/5/27.
 */
public final class ProtocolUtil {

    private static final int BYTE_MAX = 255;
    private static final int SHORT_MAX = 65535;

    private static final byte SIZE_TYPE_BYTE = 0;
    private static final byte SIZE_TYPE_SHORT = 1;


    public static String readUTFStr(ByteBuf in) {

        int utflen = readUTFBinInt(in);
        byte[] bytearr = new byte[utflen];
        in.readBytes(bytearr);
        try {
            return new String(bytearr, "utf-8");
        } catch (Exception e) {

        }
        return "";
    }


    public static void writeUTFBinary(ByteBuf out, String str) {
        if(null == str) str = "";
        byte[] bytearr = null;
        try {
            bytearr = str.getBytes("utf-8");
        } catch (Exception e) {
        }
        if (bytearr != null) {
//            writeStrArraySize(out, bytearr.length);
            out.writeShort(bytearr.length);
            out.writeBytes(bytearr);
        }
    }



    public static short readUTFBinShort(ByteBuf in) {
        byte type =  in.readByte();
        if(type < 122 && type > -128) return (short)type;
        else if(type == 122) return (short)in.readByte();
        return in.readShort();
    }

    public static int readUTFBinInt(ByteBuf in) {
        byte type =  in.readByte();
        if(type < 122 && type > -128) return (int)type;
        else if(type == 122) return (int)in.readByte();
        else if(type == 123) return (int)in.readShort();
        return in.readInt();
    }

    public static long readUTFBinLong(ByteBuf in) {
        byte type =  in.readByte();
        if(type < 122 && type > -128) return (long)type;
        else if(type == 122) return (long)in.readByte();
        else if(type == 123) return (long)in.readShort();
        else if(type == 124) return (long)in.readInt();
        return (long)in.readDouble();
    }

    public static float readUTFBinFloat(ByteBuf in) {
        byte type =  in.readByte();
        if(type < 122 && type > -128) return (float)type;
        else if(type == 122) return (float)in.readByte();
        else if(type == 123) return (float)in.readShort();
        else if(type == 124) return (float)in.readInt();
        else if(type == 125) return Float.parseFloat(String.valueOf(in.readDouble()));
        return in.readFloat();
    }

    public static double readUTFBinDouble(ByteBuf in) {
        byte type =  in.readByte();
        if(type < 122 && type > -128) return (double)type;
        else if(type == 122) return (double)in.readByte();
        else if(type == 123) return (double)in.readShort();
        else if(type == 124) return (double)in.readInt();
        else if(type == 125) return in.readDouble();
        else if(type == 126) return Double.parseDouble(String.valueOf(in.readFloat()));
        return in.readDouble();
    }

    public static void writeByte(ByteBuf out, byte val) {
        if(val < 122 && val > -128){//byte
            out.writeByte(val);
        }else{//byte
            out.writeByte(122);
            out.writeByte((byte)val);
        }
    }

    public static void writeShort(ByteBuf out, short val) {
        if(val < 122 && val > -128){//byte
            out.writeByte((byte)val);
        }else if(val < 128 && val > 122){//byte
            out.writeByte(122);
            out.writeByte((byte)val);
        }else{//short
            out.writeByte(123);
            out.writeShort(val);
        }
    }

    public static void writeInt(ByteBuf out, int val) {
        if(val < 122 && val > -128){//byte
            out.writeByte((byte)val);
        }else if(val < 128 && val > 122){//byte
            out.writeByte(122);
            out.writeByte((byte)val);
        }else if(val < 32767 && val > -32768){//short
            out.writeByte(123);
            out.writeShort((short)val);
        }else{
            out.writeByte(124);
            out.writeInt(val);
        }
    }

    public static void writeLong(ByteBuf out, long val) {
        if(val < 122 && val > -128){//byte
            out.writeByte((byte)val);
        }else if(val < 128 && val > 122){//byte
            out.writeByte(122);
            out.writeByte((byte)val);
        }else if(val < 32767 && val > -32768){//short
            out.writeByte(123);
            out.writeShort((short)val);
        }else if(val < 2147483647 && val > -2147483648){//int
            out.writeByte(124);
            out.writeInt((int)val);
        }else{
            out.writeByte(125);
            out.writeDouble((double)val);
        }
    }

    public static void writeFloat(ByteBuf out, float val) {
        out.writeByte(126);
        out.writeFloat(val);
    }

    public static void writeDouble(ByteBuf out, double val) {
        out.writeByte(127);
        out.writeDouble(val);
    }

    public static int readStrArraySize(ByteBuf in) {
        byte type = in.readByte();
        if(type < 122) return type;
        else if(type == 122) return (int)in.readByte();
        else if(type == 123) return (int)in.readShort();
        else if(type == 124) return (int)in.readInt();
        else if(type == 125) return (int)in.readDouble();
        else if(type == 126) return (int)in.readFloat();
        else if(type == 127) return (int)in.readDouble();
        return 0;
    }

    public static void writeStrArraySize(ByteBuf out, int val) {
        if(val < 122){//byte
            out.writeByte((byte)val);
        }else if(val < 128){//byte
            out.writeByte(122);
            out.writeByte((byte)val);
        }else if(val < 32767){//short
            out.writeByte(123);
            out.writeShort((short)val);
        }else if(val < 2147483647){//int
            out.writeByte(124);
            out.writeInt((int)val);
        }else{//long
            out.writeByte(125);
            out.writeDouble((double)val);
        }
    }

    public static void main(String[] args){
        double d = 2300000000d;
        long l = Math.round(d);
        System.out.println((long)d);
        //System.out.println(Long.parseLong(String.valueOf(d)));
    }
}