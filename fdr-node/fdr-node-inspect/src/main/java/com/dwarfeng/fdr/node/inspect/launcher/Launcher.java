package com.dwarfeng.fdr.node.inspect.launcher;

import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class Launcher {

    public static void main(String[] args) {
        ApplicationUtil.launch(
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml");
    }
}
