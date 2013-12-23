package qdc.cookies;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import qdc.cookies.giftbox.GiftBoxBlock;
import qdc.cookies.giftbox.GiftBoxEntity;
import qdc.cookies.giftbox.GiftBoxRenderer;
import qdc.cookies.items.ChocChip;
import qdc.cookies.items.ChocolatePowder;
import qdc.cookies.items.CookieDough;
import qdc.cookies.items.CookieGeneric;
import qdc.cookies.items.CutterGeneric;
import qdc.cookies.items.GingerPowder;
import qdc.cookies.items.ItemSeedFoodGinger;
import qdc.cookies.items.SugarPowder;
import qdc.cookies.items.cookies.GBManCookie;
import qdc.cookies.items.cookies.RoundChocChipCookie;
import qdc.cookies.items.cookies.RoundDoubleChocChipCookie;
import qdc.cookies.items.cookies.RoundSugarCookie;
import qdc.cookies.items.cookies.SquareChocChip;
import qdc.cookies.items.cookies.SquareDoubleChocChipCookie;
import qdc.cookies.items.cookies.StarChocChipCookie;
import qdc.cookies.items.cookies.StarDoubleChocChipCookie;
import qdc.cookies.items.tools.Grinder;
import qdc.cookies.plants.GingerBlock;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Cookies.modid, name = "Cookies!", version = "0.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
/**
 * 
 * 
 * @author KentBen
 * 
 * @author Ralle030583 - Refactor Work
 */
public class Cookies {

	public static final String modid = "cookies";

	@Instance(value = "cookies")
	public static Cookies instance;

	@SidedProxy(clientSide = "qdc.cookies.client.ClientProxy", serverSide = "qdc.cookies.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs cookieTab;

	public static CookieWorldGen worldGen = new CookieWorldGen();

	/**
	 * Map with all Registered items.
	 */
	public static HashMap<Class, Item> cookieItems = new HashMap<Class, Item>();
	
	public static Item squareSugarCookie;
	public static Item starSugarCookie;
	public static Item roundPlainCookie;
	public static Item squarePlainCookie;
	public static Item starPlainCookie;
	public static Item christmasCookie;

	public static Item cutterCircle;
	public static Item cutterSquare;
	public static Item cutterStar;
	public static Item cutterGBMan;
	public static Item cutterXmas;

	public static Item sugarPowder;
	public static Item gingerPowder;

	public static Block gingerBlock;
	public static Item ginger;

	public static Block giftBox;

	public static final int guiGiftBox = 0;

	public static GBManCookie gbManCookie = new GBManCookie(1041);
	
	@EventHandler
	public void load(FMLInitializationEvent e) {
		
		// Creative Tab
		String tabName = "cookietab";
		cookieTab = new CreativeTabs(tabName) {
			@Override
			@SideOnly(Side.CLIENT)
			public int getTabIconItemIndex() {
				return gbManCookie.itemID;
			}
		};
		gbManCookie.setCreativeTab(Cookies.cookieTab); // GbMan addind afterwards to tab.. yeah cause its the icon xD
		
		
		LanguageRegistry.instance().addStringLocalization(
				cookieTab.getTranslatedTabLabel(), "All the Cookies!");

		// register damage handler
		GameRegistry.registerCraftingHandler(new CookieCraftingHandler());

		// Register Worldgen
		GameRegistry.registerWorldGenerator(worldGen);

		// Register Guis?
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

		// Registering Items
		this.registerItems();

		// Registering Tiles
		this.registerTiles();

		// Register all Receipes
		this.registerReceipes();

	}

	private void registerItems() {
		squareSugarCookie = new CookieGeneric(1022, 6, 0.5F, false, "sugar_cookie_square");
		starSugarCookie = new CookieGeneric(1023, 6, 0.5F, false, "sugar_cookie_star");
		roundPlainCookie = new CookieGeneric(1031, 6, 0.5F, false, "plain_cookie");
		squarePlainCookie = new CookieGeneric(1032, 6, 0.5F, false, "plain_cookie_square");
		christmasCookie = new CookieGeneric(1042, 12, 0.5F, false, "stream_xmas_tree");
		starPlainCookie = new CookieGeneric(1033, 6, 0.5F, false, "plain_cookie_star");
		
		this.registerItem(squareSugarCookie, "Square Sugar Cookie");
		this.registerItem(starSugarCookie, "Star Sugar Cookie");
		this.registerItem(roundPlainCookie, "Plain Cookie");
		this.registerItem(squarePlainCookie, "Square Cookie");
		this.registerItem(starPlainCookie, "Star Cookie");
		this.registerItem(christmasCookie, "Stream Christmas Cookie");

		// Sugar Powder
		sugarPowder = new SugarPowder(1102)
				.setUnlocalizedName("cookies:sugar_powder");
		this.registerItem(sugarPowder, "Sugar Powder");

		// Ginger Powder
		gingerPowder = new GingerPowder(1104).setUnlocalizedName(
				"cookies:grated_ginger").setCreativeTab(Cookies.cookieTab);
		this.registerItem(gingerPowder, "Ginger Powder");

		// Register Ginger Plant
		gingerBlock = new GingerBlock(1201).setUnlocalizedName("Ginger");
		this.registerBlock(gingerBlock, "Ginger");
		ginger = new ItemSeedFoodGinger(1202, 4, 0.3F, gingerBlock.blockID,
				Block.tilledField.blockID).setUnlocalizedName("cookies:ginger")
				.setCreativeTab(Cookies.cookieTab);
		this.registerItem(ginger, "Ginger");

		// Cutters
		cutterCircle = new CutterGeneric(1301, "cookie_cutter_circle");
		cutterSquare = new CutterGeneric(1302, "cookie_cutter_square");
		cutterStar   = new CutterGeneric(1304, "cookie_cutter_star");
		cutterGBMan  = new CutterGeneric(1305, "cookie_cutter_gingerbread_man");
		cutterXmas   = new CutterGeneric(1306, "xmas_tree_cutter");
		
		this.registerItem(cutterCircle, "Round Cookie Cutter");
		this.registerItem(cutterSquare, "Square Cookie Cutter");
		this.registerItem(cutterStar, "Star Cookie Cutter");
		this.registerItem(cutterGBMan, "Gingerbread Man Cutter");
		this.registerItem(cutterXmas, "Christmas Tree Cutter");

		// Tools
		cookieItems.put(Grinder.class, new Grinder(1320).register());
		// Adding reagents
		cookieItems.put(CookieDough.class, new CookieDough(1101).register());
		cookieItems.put(ChocChip.class, new ChocChip(1105).register());
		cookieItems.put(ChocolatePowder.class,new ChocolatePowder(1103).register());
		
		// Adding Cookies!
		cookieItems.put(StarChocChipCookie.class, new StarChocChipCookie(1003).register());
		cookieItems.put(StarDoubleChocChipCookie.class, new StarDoubleChocChipCookie(1013).register());
		cookieItems.put(RoundChocChipCookie.class, new RoundChocChipCookie(1001).register());
		cookieItems.put(RoundDoubleChocChipCookie.class, new RoundDoubleChocChipCookie(1011).register());
		cookieItems.put(GBManCookie.class, gbManCookie.register());
		cookieItems.put(RoundSugarCookie.class, new RoundSugarCookie(1024).register());
		cookieItems.put(SquareChocChip.class, new SquareChocChip(1002).register());
		cookieItems.put(SquareDoubleChocChipCookie.class, new SquareDoubleChocChipCookie(1012).register());
	}

	private void registerTiles() {
		// Tile Entities

		// proxy.registerRenderers();

		GameRegistry.registerTileEntity(GiftBoxEntity.class, "giftbox");

		ClientRegistry.bindTileEntitySpecialRenderer(GiftBoxEntity.class,
				new GiftBoxRenderer());

		giftBox = new GiftBoxBlock(1400, Material.wood);
		this.registerBlock(giftBox, "Gift Box");
	}

	private void registerReceipes() {
		// Ground Items
		ItemStack tempGrinder = new ItemStack(Cookies.cookieItems.get(Grinder.class), 1,
				OreDictionary.WILDCARD_VALUE);

		GameRegistry.addShapelessRecipe(new ItemStack(Cookies.gingerPowder, 2),
				tempGrinder, Cookies.ginger);
		GameRegistry.addShapelessRecipe(new ItemStack(Cookies.sugarPowder, 2),
				tempGrinder, Item.sugar);
		GameRegistry.addShapelessRecipe(new ItemStack(cookieItems.get(ChocolatePowder.class), 2),
				tempGrinder, cookieItems.get(ChocChip.class));

		// cookie dough
		GameRegistry.addShapelessRecipe(new ItemStack(Cookies.cookieItems.get(CookieDough.class), 2),
				Item.wheat, Item.sugar, Item.bucketMilk);

		// Tools
		GameRegistry.addRecipe(new ItemStack(Cookies.cutterCircle), " x ",
				"xyx", " x ", 'x', Item.ingotIron, 'y', Block.wood);
		GameRegistry.addRecipe(new ItemStack(Cookies.cutterSquare), " x ",
				"xyx", " x ", 'x', Item.ingotGold, 'y', Block.wood);
		GameRegistry.addRecipe(new ItemStack(Cookies.cutterStar), " x ", "xyx",
				" x ", 'x', Item.diamond, 'y', Block.wood);
		GameRegistry.addRecipe(new ItemStack(Cookies.cutterGBMan), " x ",
				"xyx", " x ", 'x', Item.emerald, 'y', Block.wood);
		GameRegistry.addRecipe(new ItemStack(Cookies.cutterXmas), " x ", "xyx",
				" x ", 'x', new ItemStack(Item.dyePowder, 1, 4), 'y',
				Block.wood);

		GameRegistry.addRecipe(new ItemStack(Cookies.cookieItems.get(Grinder.class)), " s ", "wcw",
				"ooo", 'w', Block.wood, 'c', Cookies.cutterCircle, 'o',
				Block.cobblestone, 's', Item.stick);
		GameRegistry.addRecipe(new ItemStack(Cookies.christmasCookie), " g ",
				"ybr", "dsc", 'g', new ItemStack(Item.dyePowder, 1, 2), 'y',
				new ItemStack(Item.dyePowder, 1, 11), 'b', new ItemStack(
						Item.dyePowder, 1, 4), 'r', new ItemStack(
						Item.dyePowder, 1, 1), 'c', Cookies.cutterXmas, 'd',
				Cookies.cookieItems.get(CookieDough.class), 's', Cookies.sugarPowder);

		// Cookies
		GameRegistry.addShapelessRecipe(new ItemStack(Cookies.cookieItems.get(ChocChip.class), 2), // TODO
				tempGrinder, new ItemStack(Item.dyePowder, 1, 3));
		GameRegistry.addShapelessRecipe(
				new ItemStack(Cookies.roundPlainCookie), Cookies.cookieItems.get(CookieDough.class),
				Cookies.cutterCircle);
		GameRegistry.addShapelessRecipe(
				new ItemStack(Cookies.squarePlainCookie), Cookies.cookieItems.get(CookieDough.class),
				Cookies.cutterSquare);
		GameRegistry.addShapelessRecipe(new ItemStack(Cookies.starPlainCookie),
				Cookies.cookieItems.get(CookieDough.class), Cookies.cutterStar);

		GameRegistry.addShapelessRecipe(
				new ItemStack(Cookies.squareSugarCookie), Cookies.cookieItems.get(CookieDough.class),
				Cookies.cutterSquare, Cookies.sugarPowder);
		GameRegistry.addShapelessRecipe(new ItemStack(Cookies.starSugarCookie),
				Cookies.cookieItems.get(CookieDough.class), Cookies.cutterStar, Cookies.sugarPowder);

	}

	public void registerBlock(Block block, String name) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName());
		LanguageRegistry.addName(block, name);

	}

	public void registerItem(Item item, String name) {
		GameRegistry.registerItem(item, item.getUnlocalizedName());
		LanguageRegistry.addName(item, name);

	}

}
