package com.asdru;

import com.asdru.oopack.Module;
import com.asdru.oopack.objects.assets.Item;
import com.asdru.oopack.objects.assets.Model;
import com.asdru.oopack.objects.assets.Texture;

public class Common extends Module {
    public static void makeResources(String id) {

        Item.f.ofName(id,"""
                {
                  "model": {
                    "type": "minecraft:model",
                    "model": "%s"
                  }
                }
                """, Model.f.ofName("item/","""
                {
                	"parent": "item/generated",
                	"textures": {
                		"layer0": "%s"
                	}
                }
                """, Texture.of("item/"+id)));
    }

    @Override
    protected void content() {

    }
}
