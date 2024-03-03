package me.nilt0.timbering.listener;

import me.nilt0.timbering.plugin.Timbering;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;

import java.util.*;

public class BlockBreakListener implements Listener {
    private final Timbering main;
    private final List<Material> woodMaterialList;
    private final List<Material> axeMaterialList;

    public BlockBreakListener(Timbering main){
        this.main = main;
        this.woodMaterialList = populateWoodMaterialList();
        this.axeMaterialList = populateAxeMaterialList();

        registerBlockBreakListenerEvent();
    }

    @EventHandler
    public void onEvent(BlockBreakEvent blockBreakEvent){
        Block blockBeingBroken = blockBreakEvent.getBlock();
        Player player = blockBreakEvent.getPlayer();
        ItemStack itemUsedToBreakTheBlock = player.getInventory().getItemInMainHand();

        if (isLogBlock(blockBeingBroken) && itemUsedIsAxe(itemUsedToBreakTheBlock) && !isPlayerCrouching(player))
            BreakWholeTree(blockBeingBroken, player);
    }

    private void BreakWholeTree(Block block, Player player){
        List<Location> locationsToBreak;
        GameMode playerGameMode = player.getGameMode();
        locationsToBreak = getWoodenBlockLocations(block);

        if (playerGameMode == GameMode.SURVIVAL) {
            locationsToBreak.forEach(location -> location.getBlock().breakNaturally());
            return;
        }

        if (playerGameMode == GameMode.CREATIVE)
            locationsToBreak.forEach(location -> location.getBlock().setType(Material.AIR));
    }

    private void registerBlockBreakListenerEvent(){
        main.getServer().getPluginManager().registerEvents(this, main);
        Bukkit.getLogger().info("[Timbering] Registered Event");
    }

    private Boolean itemUsedIsAxe(ItemStack item){
        return this.axeMaterialList.contains(item.getType());
    }

    private Boolean isLogBlock(Block block){
        return this.woodMaterialList.contains(block.getType());
    }

    private Boolean isPlayerCrouching(Player player){
        return player.isSneaking();
    }

    private List<Material> populateWoodMaterialList(){
        List<Material> materialList = new ArrayList<>();

        materialList.add(Material.OAK_LOG);
        materialList.add(Material.SPRUCE_LOG);
        materialList.add(Material.BIRCH_LOG);
        materialList.add(Material.JUNGLE_LOG);
        materialList.add(Material.ACACIA_LOG);
        materialList.add(Material.DARK_OAK_LOG);
        materialList.add(Material.MANGROVE_LOG);
        materialList.add(Material.CHERRY_LOG);
        materialList.add(Material.CRIMSON_HYPHAE);
        materialList.add(Material.WARPED_HYPHAE);

        return materialList;
    }

    private List<Material> populateAxeMaterialList(){
        List<Material> materialList = new ArrayList<>();

        materialList.add(Material.NETHERITE_AXE);
        materialList.add(Material.DIAMOND_AXE);
        materialList.add(Material.GOLDEN_AXE);
        materialList.add(Material.IRON_AXE);
        materialList.add(Material.STONE_AXE);
        materialList.add(Material.WOODEN_AXE);

        return materialList;
    }

    private List<Location> getWoodenBlockLocations(Block block){
        List<Location> woodenBlockLocationList = new ArrayList<>();
        List<Location> allWoodenBlocksInYAxis = getAllWoodenBlocksLocationsAroundBlock(block);

        if (allWoodenBlocksInYAxis.isEmpty())
            return woodenBlockLocationList;

        woodenBlockLocationList.addAll(allWoodenBlocksInYAxis);

        Block nextBlock;
        double yAxis = 1.0;

        do {
            nextBlock = getNextBlockY(block, yAxis);
            allWoodenBlocksInYAxis = getAllWoodenBlocksLocationsAroundBlock(nextBlock);

            if (!allWoodenBlocksInYAxis.isEmpty())
                woodenBlockLocationList.addAll(allWoodenBlocksInYAxis);

            yAxis += 1.0;
        }while(!allWoodenBlocksInYAxis.isEmpty());

        return woodenBlockLocationList;
    }

    private List<Location> getAllWoodenBlocksLocationsAroundBlock(Block block){
        List<Location> locationList = new ArrayList<>();
        if (isLogBlock(block))
           locationList.add(block.getLocation());

        for (double x = -3.0; x < 3.0; x += 1.0) {
            for (double z = -3.0; z < 3.0; z += 1.0) {
                Block newBlock = getNextBlockXAndZ(block, x, z);
                if (isLogBlock(newBlock))
                    locationList.add(newBlock.getLocation());
            }
        }

        return locationList;
    }

    private Block getNextBlockY(Block block, double yAxis){
        return block.getLocation().add(0.0, yAxis, 0.0).getBlock();
    }

    private Block getNextBlockXAndZ(Block block, double xAxis, double zAxis){
        return block.getLocation().add(xAxis, 0.0, zAxis).getBlock();
    }

}
