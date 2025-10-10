package com.asdru;

import com.asdru.oopack.Module;
import com.asdru.oopack.objects.assets.Sound;
import com.asdru.oopack.objects.data.Function;
import com.asdru.oopack.util.Util;

public class MostraRaggio extends Module {

    @Override
    protected void content() {

        String id = "show_beam";
        Common.makeResources(id);

        makeSinLookup();

        // quando il datapack viene caricato, aggiungo uno score in grado di tracciare click con il tasto destro del mouse
        Util.setOnLoad(Function.f.of("""
                scoreboard objectives add $ns$.click minecraft.used:minecraft.warped_fungus_on_a_stick
                scoreboard objectives add $ns$.var dummy
                """));

        var tick = Function.f.of("execute as @a at @s run function %s",
                Function.f.of("""
                                execute if score @s $ns$.click matches 1.. run function %s
                                scoreboard players reset @s $ns$.click
                                """,
                        Function.f.of("""
                                        execute unless items entity @s container.* *[minecraft:custom_data~{$ns$:{ammo:1b}}] run return fail
                                        data remove storage $ns$:storage item
                                        function %s
                                        execute if data storage $ns$:storage item run function %s
                                        """, Function.f.of(getSlot()),
                                Function.f.of("""
                                                execute store result score $distance $ns$.var run data get storage $ns$:storage item.components."minecraft:custom_data".$ns$.distance 10
                                                playsound %s player @a[distance=..16]
                                                function %s with storage $ns$:storage item.components."minecraft:custom_data".$ns$
                                                execute anchored eyes positioned ^ ^ ^ run function %s
                                                """, Util.addSound(
                                                "item.%s".formatted(id),
                                                "Beam Sparkles",
                                                Sound.of("item/%s".formatted(id)
                                                )
                                        ),
                                        Function.f.of("""
                                                $clear @s *[minecraft:custom_data~{$ns$:{distance:$(distance)b}}] 1
                                                """),
                                        Function.f.of("""
                                                scoreboard players remove $distance $ns$.var 1
                                                execute store result storage $ns$:storage distance.amount int 1 run scoreboard players get $distance $ns$.var
                                                function %s with storage $ns$:storage distance
                                                execute if score $distance $ns$.var matches 1.. positioned ^ ^ ^0.1 run function $ns$:$name$
                                                """,
                                                Function.f.of("""
                                                        $function %s with storage esempio:storage sin[$(amount)]
                                                        """,
                                                        Function.f.of("""
                                                                $particle end_rod ^ ^$(value) ^
                                                                """)))
                                )
                        )
                )
        );

        Util.setOnTick(tick);
    }

    private String getSlot() {
        var slots = new StringBuilder();
        for (int i = 0; i <= 35; i++) {
            slots.append("""
                    execute if items entity @s container.%1$s *[minecraft:custom_data~{$ns$:{ammo:1b}}] run return run data modify storage $ns$:storage item set from entity @s Inventory[{Slot:%1$sb}]
                    """.formatted(i)
            );
        }
        return slots.toString();
    }

    private void makeSinLookup(){
        StringBuilder sin = new StringBuilder("data modify storage esempio:storage sin set value [");

        for (int i = 0; i <= 360; i++) {
            sin.append("{value:").append(Math.sin(Math.toRadians(i*10))).append("},");
        }
        sin.append("]");
        Util.setOnLoad(Function.f.of(sin.toString()));
    }
}
