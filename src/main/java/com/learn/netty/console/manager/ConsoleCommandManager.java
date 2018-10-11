package com.learn.netty.console.manager;

import com.learn.netty.console.*;
import io.netty.channel.Channel;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author :lwy
 * @Date : 2018/10/10 15:50
 * @Description :
 */
public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupConsoleCommand());
        consoleCommandMap.put("listGroup", new ListGroupMembersConsoleCommand());
        consoleCommandMap.put("sendGroup", new GroupMessageConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {

        //获取第一个指令
        String command = scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }


}
