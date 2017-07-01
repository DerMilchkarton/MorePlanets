package stevekung.mods.moreplanets.module.planets.nibiru.entity.ai;

import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import stevekung.mods.moreplanets.module.planets.nibiru.entity.EntityNibiruVillager;

public class EntityAINibiruVillagerInteract extends EntityAIWatchClosest2
{
    private int interactionDelay;
    private EntityNibiruVillager villager;

    public EntityAINibiruVillagerInteract(EntityNibiruVillager villager)
    {
        super(villager, EntityNibiruVillager.class, 3.0F, 0.02F);
        this.villager = villager;
    }

    @Override
    public void startExecuting()
    {
        super.startExecuting();

        if (this.villager.canAbondonItems() && this.closestEntity instanceof EntityNibiruVillager && ((EntityNibiruVillager)this.closestEntity).func_175557_cr())
        {
            this.interactionDelay = 10;
        }
        else
        {
            this.interactionDelay = 0;
        }
    }

    @Override
    public void updateTask()
    {
        super.updateTask();

        if (this.interactionDelay > 0)
        {
            --this.interactionDelay;

            if (this.interactionDelay == 0)
            {
                InventoryBasic inventorybasic = this.villager.getVillagerInventory();

                for (int i = 0; i < inventorybasic.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = inventorybasic.getStackInSlot(i);
                    ItemStack itemstack1 = null;

                    if (itemstack != null)
                    {
                        Item item = itemstack.getItem();

                        if ((item == Items.BREAD || item == Items.POTATO || item == Items.CARROT) && itemstack.getCount() > 3)
                        {
                            int l = itemstack.getCount() / 2;
                            itemstack.shrink(l);
                            itemstack1 = new ItemStack(item, l, itemstack.getMetadata());
                        }
                        else if (item == Items.WHEAT && itemstack.getCount() > 5)
                        {
                            int j = itemstack.getCount() / 2 / 3 * 3;
                            int k = j / 3;
                            itemstack.shrink(j);
                            itemstack1 = new ItemStack(Items.BREAD, k, 0);
                        }

                        if (itemstack.getCount() <= 0)
                        {
                            inventorybasic.setInventorySlotContents(i, ItemStack.EMPTY);
                        }
                    }

                    if (itemstack1 != null)
                    {
                        double d0 = this.villager.posY - 0.30000001192092896D + this.villager.getEyeHeight();
                        EntityItem entityitem = new EntityItem(this.villager.world, this.villager.posX, d0, this.villager.posZ, itemstack1);
                        float f = 0.3F;
                        float f1 = this.villager.rotationYawHead;
                        float f2 = this.villager.rotationPitch;
                        entityitem.motionX = -MathHelper.sin(f1 / 180.0F * (float)Math.PI) * MathHelper.cos(f2 / 180.0F * (float)Math.PI) * f;
                        entityitem.motionZ = MathHelper.cos(f1 / 180.0F * (float)Math.PI) * MathHelper.cos(f2 / 180.0F * (float)Math.PI) * f;
                        entityitem.motionY = -MathHelper.sin(f2 / 180.0F * (float)Math.PI) * f + 0.1F;
                        entityitem.setDefaultPickupDelay();
                        this.villager.world.spawnEntity(entityitem);
                        break;
                    }
                }
            }
        }
    }
}