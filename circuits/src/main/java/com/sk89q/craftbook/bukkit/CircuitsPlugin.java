// $Id$
/*
 * Copyright (C) 2010, 2011 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.craftbook.bukkit;

import java.io.File;
import java.util.Map.Entry;

import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.World;

import com.sk89q.craftbook.CircuitsConfiguration;
import com.sk89q.craftbook.LanguageManager;
import com.sk89q.craftbook.Mechanic;
import com.sk89q.craftbook.MechanicFactory;
import com.sk89q.craftbook.MechanicManager;
import com.sk89q.craftbook.bukkit.commands.CircuitCommands;
import com.sk89q.craftbook.circuits.GlowStone;
import com.sk89q.craftbook.circuits.JackOLantern;
import com.sk89q.craftbook.circuits.Netherrack;
import com.sk89q.craftbook.gates.logic.AndGate;
import com.sk89q.craftbook.gates.logic.Clock;
import com.sk89q.craftbook.gates.logic.ClockDivider;
import com.sk89q.craftbook.gates.logic.Counter;
import com.sk89q.craftbook.gates.logic.Delayer;
import com.sk89q.craftbook.gates.logic.DownCounter;
import com.sk89q.craftbook.gates.logic.EdgeTriggerDFlipFlop;
import com.sk89q.craftbook.gates.logic.InvertedRsNandLatch;
import com.sk89q.craftbook.gates.logic.Inverter;
import com.sk89q.craftbook.gates.logic.JkFlipFlop;
import com.sk89q.craftbook.gates.logic.LevelTriggeredDFlipFlop;
import com.sk89q.craftbook.gates.logic.LowDelayer;
import com.sk89q.craftbook.gates.logic.LowNotPulser;
import com.sk89q.craftbook.gates.logic.LowPulser;
import com.sk89q.craftbook.gates.logic.Marquee;
import com.sk89q.craftbook.gates.logic.Monostable;
import com.sk89q.craftbook.gates.logic.Multiplexer;
import com.sk89q.craftbook.gates.logic.NandGate;
import com.sk89q.craftbook.gates.logic.NotDelayer;
import com.sk89q.craftbook.gates.logic.NotLowDelayer;
import com.sk89q.craftbook.gates.logic.NotPulser;
import com.sk89q.craftbook.gates.logic.Pulser;
import com.sk89q.craftbook.gates.logic.Random3Bit;
import com.sk89q.craftbook.gates.logic.RandomBit;
import com.sk89q.craftbook.gates.logic.Repeater;
import com.sk89q.craftbook.gates.logic.RsNandLatch;
import com.sk89q.craftbook.gates.logic.RsNorFlipFlop;
import com.sk89q.craftbook.gates.logic.ToggleFlipFlop;
import com.sk89q.craftbook.gates.logic.XnorGate;
import com.sk89q.craftbook.gates.logic.XorGate;
import com.sk89q.craftbook.gates.weather.RainSensor;
import com.sk89q.craftbook.gates.weather.RainSensorST;
import com.sk89q.craftbook.gates.weather.TStormSensor;
import com.sk89q.craftbook.gates.weather.TStormSensorST;
import com.sk89q.craftbook.gates.weather.WeatherControl;
import com.sk89q.craftbook.gates.weather.WeatherControlAdvanced;
import com.sk89q.craftbook.gates.weather.WeatherFaker;
import com.sk89q.craftbook.gates.world.ArrowBarrage;
import com.sk89q.craftbook.gates.world.ArrowShooter;
import com.sk89q.craftbook.gates.world.BlockSensor;
import com.sk89q.craftbook.gates.world.BlockSensorST;
import com.sk89q.craftbook.gates.world.ChestCollector;
import com.sk89q.craftbook.gates.world.ChestCollectorST;
import com.sk89q.craftbook.gates.world.ChestDispenser;
import com.sk89q.craftbook.gates.world.CombinationLock;
import com.sk89q.craftbook.gates.world.CreatureSpawner;
import com.sk89q.craftbook.gates.world.DaySensor;
import com.sk89q.craftbook.gates.world.DaySensorST;
import com.sk89q.craftbook.gates.world.EntitySensor;
import com.sk89q.craftbook.gates.world.EntitySensorST;
import com.sk89q.craftbook.gates.world.EntityTrap;
import com.sk89q.craftbook.gates.world.EntityTrapST;
import com.sk89q.craftbook.gates.world.FireBarrage;
import com.sk89q.craftbook.gates.world.FireShooter;
import com.sk89q.craftbook.gates.world.FlexibleSetBlock;
import com.sk89q.craftbook.gates.world.ItemDispenser;
import com.sk89q.craftbook.gates.world.ItemNotSensor;
import com.sk89q.craftbook.gates.world.ItemNotSensorST;
import com.sk89q.craftbook.gates.world.ItemSensor;
import com.sk89q.craftbook.gates.world.ItemSensorST;
import com.sk89q.craftbook.gates.world.LavaSensor;
import com.sk89q.craftbook.gates.world.LavaSensorST;
import com.sk89q.craftbook.gates.world.LightSensor;
import com.sk89q.craftbook.gates.world.LightSensorST;
import com.sk89q.craftbook.gates.world.LightningSummon;
import com.sk89q.craftbook.gates.world.Melody;
import com.sk89q.craftbook.gates.world.MessageSender;
import com.sk89q.craftbook.gates.world.MultipleSetBlock;
import com.sk89q.craftbook.gates.world.ParticleEffect;
import com.sk89q.craftbook.gates.world.ParticleEffectST;
import com.sk89q.craftbook.gates.world.PlayerDetection;
import com.sk89q.craftbook.gates.world.PlayerDetectionST;
import com.sk89q.craftbook.gates.world.PotionInducer;
import com.sk89q.craftbook.gates.world.PowerSensor;
import com.sk89q.craftbook.gates.world.PowerSensorST;
import com.sk89q.craftbook.gates.world.RangedOutput;
import com.sk89q.craftbook.gates.world.ServerTimeModulus;
import com.sk89q.craftbook.gates.world.SetBlockAbove;
import com.sk89q.craftbook.gates.world.SetBlockAboveChest;
import com.sk89q.craftbook.gates.world.SetBlockBelow;
import com.sk89q.craftbook.gates.world.SetBlockBelowChest;
import com.sk89q.craftbook.gates.world.SetBridge;
import com.sk89q.craftbook.gates.world.SetDoor;
import com.sk89q.craftbook.gates.world.SoundEffect;
import com.sk89q.craftbook.gates.world.TimeControl;
import com.sk89q.craftbook.gates.world.TimeControlAdvanced;
import com.sk89q.craftbook.gates.world.TimeFaker;
import com.sk89q.craftbook.gates.world.WaterSensor;
import com.sk89q.craftbook.gates.world.WaterSensorST;
import com.sk89q.craftbook.gates.world.WirelessReceiver;
import com.sk89q.craftbook.gates.world.WirelessReceiverST;
import com.sk89q.craftbook.gates.world.WirelessTransmitter;
import com.sk89q.craftbook.ic.ICFactory;
import com.sk89q.craftbook.ic.ICFamily;
import com.sk89q.craftbook.ic.ICManager;
import com.sk89q.craftbook.ic.ICMechanicFactory;
import com.sk89q.craftbook.ic.RegisteredICFactory;
import com.sk89q.craftbook.ic.families.Family3I3O;
import com.sk89q.craftbook.ic.families.Family3ISO;
import com.sk89q.craftbook.ic.families.FamilyAISO;
import com.sk89q.craftbook.ic.families.FamilySI3O;
import com.sk89q.craftbook.ic.families.FamilySISO;
import com.sk89q.craftbook.ic.families.FamilyVIVO;
import com.sk89q.craftbook.plc.PlcFactory;
import com.sk89q.craftbook.plc.lang.Perlstone;
import com.sk89q.wepif.PermissionsResolverManager;
// import com.sk89q.bukkit.migration.*;

/**
 * Plugin for CraftBook's redstone additions.
 *
 * @author sk89q
 */
public class CircuitsPlugin extends BaseBukkitPlugin {

    protected CircuitsConfiguration config;
    private ICManager icManager;
    private PermissionsResolverManager perms;
    private MechanicManager manager;
    private static CircuitsPlugin instance;

    public static Server server;

    public static CircuitsPlugin getInst() {

        return instance;
    }

    @Override
    public void onEnable() {

        super.onEnable();

        instance = this;
        server = getServer();

        registerCommand(CircuitCommands.class);

        createDefaultConfiguration("config.yml", false);
        createDefaultConfiguration("custom-ics.txt", false);
        config = new CircuitsConfiguration(getConfig(), getDataFolder());
        saveConfig();

        languageManager = new LanguageManager(this);

        PermissionsResolverManager.initialize(this);
        perms = PermissionsResolverManager.getInstance();

        manager = new MechanicManager(this);
        MechanicListenerAdapter adapter = new MechanicListenerAdapter(this);
        adapter.register(manager);

        File midi = new File(getDataFolder(), "midi/");
        if (!midi.exists()) midi.mkdir();

        registerICs();

        // Let's register mechanics!
        if (config.enableNetherstone) registerMechanic(new Netherrack.Factory());
        if (config.enablePumpkins) registerMechanic(new JackOLantern.Factory());
        if (config.enableGlowStone) registerMechanic(new GlowStone.Factory(this));
        if (config.enableICs) {
            registerMechanic(new ICMechanicFactory(this, icManager));
            setupSelfTriggered();
        }

        // Register events
        registerEvents();
    }

    /**
     * Register ICs.
     */
    private void registerICs() {

        Server server = getServer();

        // Let's register ICs!
        icManager = new ICManager();
        ICFamily familySISO = new FamilySISO();
        ICFamily family3ISO = new Family3ISO();
        ICFamily familySI3O = new FamilySI3O();
        ICFamily familyAISO = new FamilyAISO();
        ICFamily family3I3O = new Family3I3O();
        ICFamily familyVIVO = new FamilyVIVO();

        //SISOs
        registerIC("MC1000", "repeater"      , new Repeater.Factory(server), familySISO, familyAISO);
        registerIC("MC1001", "inverter"      , new Inverter.Factory(server), familySISO, familyAISO);
        registerIC("MC1017", "re t flip"     , new ToggleFlipFlop.Factory(server, true), familySISO, familyAISO);
        registerIC("MC1018", "fe t flip"     , new ToggleFlipFlop.Factory(server, false), familySISO, familyAISO);
        registerIC("MC1020", "random bit"    , new RandomBit.Factory(server), familySISO, familyAISO);
        registerIC("MC1025", "server time"   , new ServerTimeModulus.Factory(server), familySISO, familyAISO);
        registerIC("MC1110", "transmitter"   , new WirelessTransmitter.Factory(server), familySISO, familyAISO);
        registerIC("MC1111", "receiver"      , new WirelessReceiver.Factory(server), familySISO, familyAISO);
        registerIC("MC1200", "spawner"       , new CreatureSpawner.Factory(server), familySISO, familyAISO);     // Restricted
        registerIC("MC1201", "dispenser"     , new ItemDispenser.Factory(server), familySISO, familyAISO);       // Restricted
        registerIC("MC1202", "c dispense"    , new ChestDispenser.Factory(server), familySISO, familyAISO);      // Restricted
        registerIC("MC1203", "strike"        , new LightningSummon.Factory(server), familySISO, familyAISO);     // Restricted
        registerIC("MC1204", "trap"          , new EntityTrap.Factory(server), familySISO, familyAISO);          // Restricted
        registerIC("MC1205", "set above"     , new SetBlockAbove.Factory(server), familySISO, familyAISO);       // Restricted
        registerIC("MC1206", "set below"     , new SetBlockBelow.Factory(server), familySISO, familyAISO);       // Restricted
        registerIC("MC1207", null            , new FlexibleSetBlock.Factory(server), familySISO, familyAISO);    // Restricted
        registerIC("MC1208", null            , new MultipleSetBlock.Factory(server), familySISO, familyAISO);
        registerIC("MC1209", "collector"     , new ChestCollector.Factory(server), familySISO, familyAISO);
        registerIC("MC1210", "emitter"       , new ParticleEffect.Factory(server), familySISO, familyAISO);      // Restricted
        registerIC("MC1211", null            , new SetBridge.Factory(server), familySISO, familyAISO);           // Restricted
        registerIC("MC1212", null            , new SetDoor.Factory(server), familySISO, familyAISO);             // Restricted
        registerIC("MC1213", "sound"         , new SoundEffect.Factory(server), familySISO, familyAISO);         // Restricted
        registerIC("MC1215", null            , new SetBlockAboveChest.Factory(server), familySISO, familyAISO);  // Restricted
        registerIC("MC1216", null            , new SetBlockBelowChest.Factory(server), familySISO, familyAISO);  // Restricted
        registerIC("MC1217", null            , new PotionInducer.Factory(server), familySISO, familyAISO);
        registerIC("MC1230", "sense day"     , new DaySensor.Factory(server), familySISO, familyAISO);
        registerIC("MC1231", null            , new TimeControl.Factory(server), familySISO, familyAISO);         // Restricted
        registerIC("MC1236", null            , new WeatherFaker.Factory(server), familySISO, familyAISO);        // Restricted
        registerIC("MC1237", null            , new TimeFaker.Factory(server), familySISO, familyAISO);           // Restricted
        registerIC("MC1240", "shoot arrow"   , new ArrowShooter.Factory(server), familySISO, familyAISO);        // Restricted
        registerIC("MC1241", "shoot arrows"  , new ArrowBarrage.Factory(server), familySISO, familyAISO);        // Restricted
        registerIC("MC1250", "shoot fire"    , new FireShooter.Factory(server), familySISO, familyAISO);         // Restricted
        registerIC("MC1251", null            , new FireBarrage.Factory(server), familySISO, familyAISO);         // Restricted
        registerIC("MC1260", "sense water"   , new WaterSensor.Factory(server), familySISO, familyAISO);
        registerIC("MC1261", "sense lava"    , new LavaSensor.Factory(server), familySISO, familyAISO);
        registerIC("MC1262", "sense light"   , new LightSensor.Factory(server), familySISO, familyAISO);
        registerIC("MC1263", "sense block"   , new BlockSensor.Factory(server), familySISO, familyAISO);
        registerIC("MC1264", "sense item"    , new ItemSensor.Factory(server), familySISO, familyAISO);          // Restricted
        registerIC("MC1265", "inv sense item", new ItemNotSensor.Factory(server), familySISO, familyAISO);       // Restricted
        registerIC("MC1266", "sense power"   , new PowerSensor.Factory(server), familySISO, familyAISO);         // Restricted
        registerIC("MC1270", "melody"        , new Melody.Factory(server), familySISO, familyAISO);
        registerIC("MC1271", "sense entity"  , new EntitySensor.Factory(server), familySISO, familyAISO);        // Restricted
        registerIC("MC1272", "sense player"  , new PlayerDetection.Factory(server), familySISO, familyAISO);     // Restricted
        registerIC("MC1420", "divide clock"  , new ClockDivider.Factory(server), familySISO, familyAISO);
        registerIC("MC1510", "send message"  , new MessageSender.Factory(server), familySISO, familyAISO);
        registerIC("MC2100", "delayer"       , new Delayer.Factory(server), familySISO, familyAISO);
        registerIC("MC2101", "inv delayer"   , new NotDelayer.Factory(server), familySISO, familyAISO);
        registerIC("MC2110", "fe delayer"    , new LowDelayer.Factory(server), familySISO, familyAISO);
        registerIC("MC2111", "inv fe delayer", new NotLowDelayer.Factory(server), familySISO, familyAISO);
        registerIC("MC2500", "pulser"        , new Pulser.Factory(server), familySISO, familyAISO);
        registerIC("MC2501", "inv pulser"    , new NotPulser.Factory(server), familySISO, familyAISO);
        registerIC("MC2510", "fe pulser"     , new LowPulser.Factory(server), familySISO, familyAISO);
        registerIC("MC2511", "inv fe pulser" , new LowNotPulser.Factory(server), familySISO, familyAISO);

        //SI3Os
        registerIC("MC2020", "random"        , new Random3Bit.Factory(server), familySI3O);
        registerIC("MC2999", null            , new Marquee.Factory(server), familySI3O);

        //3ISOs
        registerIC("MC3002", "and"           , new AndGate.Factory(server), family3ISO);
        registerIC("MC3003", "nand"          , new NandGate.Factory(server), family3ISO);
        registerIC("MC3020", "xor"           , new XorGate.Factory(server), family3ISO);
        registerIC("MC3021", "xnor"          , new XnorGate.Factory(server), family3ISO);
        registerIC("MC3030", "nor flip"      , new RsNorFlipFlop.Factory(server), family3ISO);
        registerIC("MC3031", "inv nand latch", new InvertedRsNandLatch.Factory(server), family3ISO);
        registerIC("MC3032", "jk flip"       , new JkFlipFlop.Factory(server), family3ISO);
        registerIC("MC3033", "nand latch"    , new RsNandLatch.Factory(server), family3ISO);
        registerIC("MC3034", "edge df flip"  , new EdgeTriggerDFlipFlop.Factory(server), family3ISO);
        registerIC("MC3036", "level df flip" , new LevelTriggeredDFlipFlop.Factory(server), family3ISO);
        registerIC("MC3040", "multiplexer"   , new Multiplexer.Factory(server), family3ISO);
        registerIC("MC3050", "combo"         , new CombinationLock.Factory(server), family3ISO);
        registerIC("MC3101", null            , new DownCounter.Factory(server), family3ISO);
        registerIC("MC3102", null            , new Counter.Factory(server), family3ISO);
        registerIC("MC3231", null            , new TimeControlAdvanced.Factory(server), family3ISO);             // Restricted
        //Missing: 3231                                                                                // Restricted
        //3I3Os
        //Missing: 4000
        //Missing: 4010
        //Missing: 4100
        //Missing: 4110
        //Missing: 4200

        //PLCs
        registerIC("MC5000", "perlstone"     , PlcFactory.fromLang(server, new Perlstone(), false), familyVIVO);
        registerIC("MC5001", "perlstone 3i3o", PlcFactory.fromLang(server, new Perlstone(), false), family3I3O);

        //Self triggered
        registerIC("MC0111", "receiver st"   , new WirelessReceiverST.Factory(server), familySISO);
        registerIC("MC0204", "trap st"       , new EntityTrapST.Factory(server), familySISO);                    // Restricted
        registerIC("MC0209", "collector st"  , new ChestCollectorST.Factory(server), familySISO);
        registerIC("MC0210", "emitter st"    , new ParticleEffectST.Factory(server), familySISO);
        registerIC("MC0230", "sense day st"  , new DaySensorST.Factory(server), familySISO);
        registerIC("MC0260", "sense water st", new WaterSensorST.Factory(server), familySISO);
        registerIC("MC0261", "sense lava st" , new LavaSensorST.Factory(server), familySISO);
        registerIC("MC0262", "sense light st", new LightSensorST.Factory(server), familySISO);
        registerIC("MC0263", "sense block st", new BlockSensorST.Factory(server), familySISO);
        registerIC("MC0264", "sense item st" , new ItemSensorST.Factory(server), familySISO);                    // Restricted
        registerIC("MC0265", null            , new ItemNotSensorST.Factory(server), familySISO);                 // Restricted
        registerIC("MC0266", "sense power st", new PowerSensorST.Factory(server), familySISO);                   // Restricted
        registerIC("MC0270", "sense power st", new PowerSensorST.Factory(server), familySISO);
        registerIC("MC0271", "sense entit st", new EntitySensorST.Factory(server), familySISO);                  // Restricted
        registerIC("MC0272", "sense playe st", new PlayerDetectionST.Factory(server), familySISO);               // Restricted
        registerIC("MC0420", "clock"         , new Clock.Factory(server), familySISO);
        registerIC("MC0421", "monostable"    , new Monostable.Factory(server), familySISO);
        registerIC("MC0500", null            , new RangedOutput.Factory(server), familySISO);
        //Missing: 0020 self-triggered RNG (may cause server load issues)
        //Xtra ICs
        //SISOs
        registerIC("MCX230", null            , new RainSensor.Factory(server), familySISO);
        registerIC("MCX231", null            , new TStormSensor.Factory(server), familySISO);
        registerIC("MCX233", null            , new WeatherControl.Factory(server), familySISO);
        //3ISOs
        registerIC("MCT233", null            , new WeatherControlAdvanced.Factory(server), family3ISO);
        //Self triggered
        registerIC("MCZ230", null            , new RainSensorST.Factory(server), familySISO);
        registerIC("MCZ231", null            , new TStormSensorST.Factory(server), familySISO);
    }

    /**
     * Setup the required components of self-triggered ICs.
     */
    private void setupSelfTriggered() {

        logger.info("CraftBook: Enumerating chunks for self-triggered components...");

        long start = System.currentTimeMillis();
        int numWorlds = 0;
        int numChunks = 0;

        for (World world : getServer().getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                manager.enumerate(chunk);
                numChunks++;
            }

            numWorlds++;
        }

        long time = System.currentTimeMillis() - start;

        logger.info("CraftBook Circuits: " + numChunks + " chunk(s) for " + numWorlds + " world(s) processed "
                + "(" + Math.round(time / 1000.0 * 10) / 10 + "s elapsed)");

        // Set up the clock for self-triggered ICs.
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new MechanicClock(manager), 0, 2);
    }

    @Override
    protected void registerEvents() {

    }

    @Override
    public CircuitsConfiguration getLocalConfiguration() {

        return config;
    }

    public PermissionsResolverManager getPermissionsResolver() {

        return perms;
    }

    public String getICList() {

        String list = "";
        for (Entry<String, RegisteredICFactory> e : icManager.registered.entrySet()) {
            if (list.equalsIgnoreCase(""))
                list = e.getKey();
            else
                list = list + ", " + e.getKey();
        }
        return list;
    }

    /**
     * Register a mechanic if possible
     *
     * @param name
     * @param factory
     * @param families
     */
    private void registerIC(String name, String longName, ICFactory factory, ICFamily... families) {

        icManager.register(name, longName, factory, families);
    }

    /**
     * Register a mechanic if possible
     *
     * @param factory
     */
    private void registerMechanic(MechanicFactory<? extends Mechanic> factory) {

        manager.register(factory);
    }

    /**
     * Register a array of mechanics if possible
     *
     * @param factories
     */
    @SuppressWarnings("unused")
    private void registerMechanic(MechanicFactory<? extends Mechanic>[] factories) {

        for (MechanicFactory<? extends Mechanic> aFactory : factories) {
            registerMechanic(aFactory);
        }
    }

    /**
     * Unregister a mechanic if possible
     * TODO Ensure no remnants are left behind
     *
     * @param factory
     *
     * @return true if the mechanic was successfully unregistered.
     */
    @SuppressWarnings("unused")
    private boolean unregisterMechanic(MechanicFactory<? extends Mechanic> factory) {

        return manager.unregister(factory);
    }
}
