package tconstruct.util.player;

import java.lang.ref.WeakReference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class TPlayerStats implements IExtendedEntityProperties
{
    public static final String PROP_NAME = "TConstruct";

    public WeakReference<EntityPlayer> player;
    public int level;
    public int bonusHealth;
    public int bonusHealthClient;
    public int hunger;
    public boolean beginnerManual;
    public boolean materialManual;
    public boolean smelteryManual;
    public boolean battlesignBonus;
    public ArmorExtended armor;
    public KnapsackInventory knapsack;

    public TPlayerStats()
    {

    }

    public TPlayerStats(EntityPlayer entityplayer)
    {
        this.player = new WeakReference<EntityPlayer>(entityplayer);
        this.armor = new ArmorExtended();
        this.armor.init(entityplayer);

        this.knapsack = new KnapsackInventory();
        this.knapsack.init(entityplayer);
    }

    @Override
    public void saveNBTData (NBTTagCompound compound)
    {
        NBTTagCompound tTag = new NBTTagCompound();
        armor.saveToNBT(tTag);
        knapsack.saveToNBT(tTag);
        tTag.setBoolean("beginnerManual", this.beginnerManual);
        tTag.setBoolean("materialManual", this.materialManual);
        tTag.setBoolean("smelteryManual", this.smelteryManual);
        tTag.setBoolean("battlesignBonus", this.battlesignBonus);
        compound.setTag(PROP_NAME, tTag);
    }

    @Override
    public void loadNBTData (NBTTagCompound compound)
    {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(PROP_NAME);

        this.armor.readFromNBT(properties);
        this.knapsack.readFromNBT(properties);
        this.beginnerManual = properties.getBoolean("beginnerManual");
        this.materialManual = properties.getBoolean("materialManual");
        this.smelteryManual = properties.getBoolean("smelteryManual");
        this.battlesignBonus = properties.getBoolean("battlesignBonus");
    }

    @Override
    public void init (Entity entity, World world)
    {
    }

    public static final void register (EntityPlayer player)
    {
        player.registerExtendedProperties(TPlayerStats.PROP_NAME, new TPlayerStats(player));
    }

    public static final TPlayerStats get (EntityPlayer player)
    {
        return (TPlayerStats) player.getExtendedProperties(PROP_NAME);
    }

}
