package com.learn.netty.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Author :lwy
 * @Date : 2018/10/10 15:48
 * @Description :
 */
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);
}
