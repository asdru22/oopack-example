package com.asdru;

import com.asdru.oopack.Namespace;
import com.asdru.oopack.Project;
import com.asdru.oopack.objects.data.Function;
import com.asdru.oopack.util.Util;

class Main {
    public static void main(String[] args) {
        Project myProject = new Project.Builder()
                .projectName("esempio")
                .version("latest")
                .worldName("test esempio")
                .icon("icona")
                .addBuildPath("C:\\Users\\Ale\\AppData\\Roaming\\.minecraft")
                .build();

        Namespace.of("esempio");

        var f = Function.f.of("say hi");

        Util.setOnLoad(f);

        myProject.buildZip();
    }
}