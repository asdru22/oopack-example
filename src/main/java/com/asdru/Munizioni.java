package com.asdru;

import com.asdru.oopack.Module;
import com.asdru.oopack.objects.data.Advancement;
import com.asdru.oopack.objects.data.Recipes;
import com.asdru.oopack.util.Util;

import java.util.Locale;

public class Munizioni extends Module {
    @Override
    protected void content() {
        make("blue_ammo", "Munizione Blu", "Blue Ammo", "diamond",20);
        make("green_ammo", "Munizione Verde", "Green Ammo", "emerald",25);
        make("purple_ammo", "Munizione Viola", "Purple Ammo", "amethyst_shard",30);

    }

    private void make(String id, String it, String en, String ingredient, int distance) {
        makeRecipe(id, ingredient,distance);
        Common.makeResources(id);

        Util.addTranslation("item.esempio.%s".formatted(id), en);
        Util.addTranslation(Locale.ITALY, "item.esempio.%s".formatted(id), it);

    }



    private void makeRecipe(String id, String ingredient,int distance) {
        var recipe = Recipes.f.ofName(id, """
                {
                  "type": "minecraft:crafting_shapeless",
                  "category": "misc",
                  "ingredients": [
                    [
                      "minecraft:%1$s"
                    ],
                    [
                      "minecraft:gunpowder"
                    ]
                  ],
                  "result": {
                    "id": "minecraft:poisonous_potato",
                    "components": {
                      "minecraft:item_name": {
                        "translate": "item.$ns$.%2$s"
                      },
                      "minecraft:item_model": "$ns$:%2$s",
                      "minecraft:custom_data": {"$ns$":{"ammo":true,"distance":%3$s}}
                    },
                    "count": 1
                  }
                }
                """, ingredient, id,distance);

        Advancement.f.ofName(id + "_recipe", """
                {
                  "parent": "minecraft:recipes/root",
                  "criteria": {
                    "has_minecraft:%1$s": {
                      "conditions": {
                        "items": [
                          {
                            "items": "minecraft:%1$s"
                          }
                        ]
                      },
                      "trigger": "minecraft:inventory_changed"
                    },
                    "has_the_recipe": {
                      "conditions": {
                        "recipe": "%2$s"
                      },
                      "trigger": "minecraft:recipe_unlocked"
                    }
                  },
                  "requirements": [
                    [
                      "has_the_recipe",
                      "has_minecraft:%1$s"
                    ]
                  ],
                  "rewards": {
                    "recipes": [
                      "%2$s"
                    ]
                  }
                }
                """, ingredient, recipe);
    }
}
