package qdc.cookies.items.cookies;

import net.minecraft.item.ItemStack;
import qdc.cookies.Cookies;
import qdc.cookies.items.CookieDough;
import cpw.mods.fml.common.registry.GameRegistry;



/**
 * Star Cookie!
 * 
 * @author Ralle030583
 */
public class StarPlainCookie extends AbstractCookieItem {

	public StarPlainCookie(int itemId) {
		super(itemId);
	}

	@Override
	protected String getCookieName() {
		return "plain_cookie_star";
	}

	@Override
	protected String getIngameName() {
		return "Star Cookie";
	}

	@Override
	protected void registerReceipes() {
		GameRegistry.addShapelessRecipe(
				new ItemStack(this),
				Cookies.cookieItems.get(CookieDough.class),
				Cookies.cutterStar);
	}

}
