package com.getouo.frameworks;

import com.getouo.msgtest.Message;

public interface DataWraper {
    default Object wrap(Object o) {
        return o;
    }

    enum ResponseWraper implements DataWraper {
        PROTO {
            @Override
            public final Message.Response wrap(Object o) {
                return (Message.Response) super.wrap(o);
            }
        }
        ;

    }

    public static void main(String[] args) {
        String hodgepodge = "";
        System.err.println(hodgepodge);
    }
}
