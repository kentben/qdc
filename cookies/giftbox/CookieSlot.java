package qdc.cookies.giftbox;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import qdc.cookies.items.cookies.AbstractCookieItem;

public class CookieSlot extends Slot {

	public CookieSlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		if (par1ItemStack.getItem() instanceof AbstractCookieItem){
			return true;
		}
		return false;
	}

}
