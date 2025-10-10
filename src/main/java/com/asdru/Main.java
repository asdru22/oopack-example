package com.asdru;

import com.asdru.oopack.Module;
import com.asdru.oopack.Namespace;
import com.asdru.oopack.Project;
import com.asdru.oopack.util.Metrics;

class Main {
    public static void main(String[] args) {
        Project myProject = new Project.Builder()
                .projectName("esempio")
                .version("1.21.10")
                .worldName("OOPack test world")
                .icon("icona")
                .description("esempio tesi")
                .addBuildPath("C:\\Users\\Ale\\AppData\\Roaming\\.minecraft")
                .build();

        Project.disableLogger();

        var namespace = Namespace.of("esempio");


        Module.register(
                Common.class,
                MostraRaggio.class,
                Munizioni.class
        );

        Metrics.register();
        namespace.exit();
        myProject.buildZip();
        Metrics.get();
    }
}