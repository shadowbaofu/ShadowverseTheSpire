package shadowverse.helper;

import com.megacrit.cardcrawl.cards.AbstractCard;
import shadowverse.cards.Royal.Ambush.*;
import shadowverse.cards.Royal.Default.*;
import shadowverse.cards.Royal.Evolve.*;
import shadowverse.cards.Royal.Festive.*;
import shadowverse.cards.Royal.Hero.*;
import shadowverse.cards.Royal.Levin.*;
import shadowverse.cards.Royal.Loot.*;
import shadowverse.cards.Royal.Minion.*;
import shadowverse.cards.Royal.NatMech.*;
import shadowverse.cards.Royal.Rally.*;

import java.util.ArrayList;

public class BanCardHelper {
    private static boolean init;
    public static ArrayList<ArrayList<AbstractCard>> royalCardGroupPool = new ArrayList<>();
    public static ArrayList<ArrayList<AbstractCard>> witchCardGroupPool = new ArrayList<>();
    public static ArrayList<ArrayList<AbstractCard>> vampireCardGroupPool = new ArrayList<>();
    public static ArrayList<ArrayList<AbstractCard>> bishopCardGroupPool = new ArrayList<>();
    public static ArrayList<ArrayList<AbstractCard>> elfCardGroupPool = new ArrayList<>();
    public static ArrayList<ArrayList<AbstractCard>> nemesisCardGroupPool = new ArrayList<>();
    public static ArrayList<ArrayList<AbstractCard>> necroCardGroupPool = new ArrayList<>();

    public BanCardHelper() {
    }

    public static void init() {
        if (!init) {
            init = true;
            //基本
            royalCardGroupPool.add(new ArrayList<AbstractCard>() {
                {
                    this.add(new Valse());
                    this.add(new FlyingMessengerSquirrel());
                    this.add(new PompousSummons());
                    this.add(new ShieldPhalanx());
                    this.add(new BladeDance());
                    this.add(new StrikeproneGuardian());
                    this.add(new MirrorImage());
                    this.add(new EleganceInAction());
                    this.add(new Reinhardt());
                    this.add(new MasterDealer());
                    this.add(new Ilmisuna());
                    this.add(new FrenziedCorpsmaster());
                    this.add(new CatAdmiral());
                    this.add(new StrokeOfConviction());
                    this.add(new EmpressOfSerenity());
                    this.add(new DualbladeKnight());
                    this.add(new RadicalGunslinger());
                    this.add(new PrudentGeneral());
                    this.add(new TheChariot());
                    this.add(new Nahtnaught());
                    this.add(new Spector());
                    this.add(new MusketeersVow());
                    this.add(new Sera());
                    this.add(new Garven());
                    this.add(new WarriorWing());
                    this.add(new Erika());
                    this.add(new Kagemitsu());
                    this.add(new MonochromeEndgame());
                    this.add(new LeciaSkySaber());
                    this.add(new Weiss());
                }
            });
            //随从
            royalCardGroupPool.add(new ArrayList<AbstractCard>() {
                {
                    this.add(new RoyalBanner());
                    this.add(new SageCommander());
                    this.add(new GloriousCore());
                    this.add(new EnragedGeneral());
                    this.add(new Leonidas());
                    this.add(new FrontlineInstructor());
                    this.add(new WhitePaladin());
                    this.add(new Latham());
                    this.add(new Arthur());
                    this.add(new Mars());
                    this.add(new CaptainWalfrid());
                    this.add(new GeneralMaximus());
                    this.add(new WardenOfHonor());
                    this.add(new Radiel());
                    this.add(new Gawain());
                }
            });
            //进化
            royalCardGroupPool.add(new ArrayList<AbstractCard>() {
                {
                    this.add(new Charlotta());
                    this.add(new Ernesta());
                    this.add(new Mirin());
                    this.add(new FortressStrategist());
                    this.add(new HonoredFrontguardGeneral());
                    this.add(new LuminousMage());
                    this.add(new Eahta());
                    this.add(new Seofon());
                    this.add(new Alyaska());
                    this.add(new LuxbladeArriet());
                }
            });
            //财宝
            royalCardGroupPool.add(new ArrayList<AbstractCard>() {
                {
                    this.add(new DiscipleOfUsurpation());
                    this.add(new UsurpingSpineblade());
                    this.add(new AdherentOfHollow());
                    this.add(new WeeMerchantsAppraisal());
                    this.add(new ApostleOfUsurpation());
                    this.add(new UltimateHollow());
                    this.add(new GrandAcquisition());
                    this.add(new DanceOfUsurpation());
                    this.add(new Octrice());
                    this.add(new Alwida());
                    this.add(new DeepSeaScout());
                    this.add(new TidalGunner());
                    this.add(new StormWrackedFirstMate());
                    this.add(new Barbaros());
                }
            });
            //雷维翁
            royalCardGroupPool.add(new ArrayList<AbstractCard>() {
                {
                    this.add(new LevinArcher());
                    this.add(new Jeno());
                    this.add(new LevinScholar());
                    this.add(new Lounes());
                    this.add(new MeetTheLevinSisters());
                    this.add(new LevinJustice());
                    this.add(new LevinBeastmaster());
                    this.add(new Albert());
                }
            });
            //机械自然
            royalCardGroupPool.add(new ArrayList<AbstractCard>() {
                {
                    this.add(new Cybercannoneer());
                    this.add(new BrothersUnited());
                    this.add(new TemperedMana());
                    this.add(new StampedingFortress());
                    this.add(new SunnyDayEncounter());
                    this.add(new DramaticRetreat());
                    this.add(new Johann());
                    this.add(new Grayson());
                    this.add(new MistolinaBayleon());
                    this.add(new Patrick());
                }
            });
            //潜伏
            royalCardGroupPool.add(new ArrayList<AbstractCard>() {
                {
                    this.add(new ShinobiTanuki());
                    this.add(new ShieldOfFlame());
                    this.add(new ShadowedMemories());
                    this.add(new Dualblade());
                    this.add(new Leod());
                    this.add(new AmbushBuff());
                    this.add(new Tsubaki());
                    this.add(new GracefulManeuver());
                }
            });
            //金币
            royalCardGroupPool.add(new ArrayList<AbstractCard>() {
                {
                    this.add(new FrontDeskFrog());
                    this.add(new SuaveBandit());
                    this.add(new HonorableThief());
                    this.add(new NightOnTheTown());
                    this.add(new MasterfulMusician());
                    this.add(new NobleShieldmaiden());
                    this.add(new JiemonThiefLord());
                    this.add(new Taketsumi());
                    this.add(new OpulentStrategist());
                }
            });
            //英雄
            royalCardGroupPool.add(new ArrayList<AbstractCard>() {
                {
                    this.add(new MorgensternMaid());
                    this.add(new FlameSoldier());
                    this.add(new MachKnight());
                    this.add(new ReturnFromTheBrink());
                    this.add(new AmerroSpearKnight());
                    this.add(new HeroicEntry());
                    this.add(new ValiantFencer());
                    this.add(new HeroOfAntiquity());
                    this.add(new Alexander());
                    this.add(new Arthur());
                    this.add(new IronwroughtDefender());
                    this.add(new Windslasher());
                    this.add(new FighterFortitude());
                }
            });
//
//            //基本
//            witchCardGroupPool.add(new ArrayList<AbstractCard>(){
//                {
//                    this.add(new ChainLightning());
//                    this.add(new Concentration());
//                    this.add(new ConjureTwosome());
//                    this.add(new MistolinasSwordplay());
//                    this.add(new StaffOfWhirlwinds());
//                    this.add(new MysterianKnowledge());
//                    this.add(new Petrification());
//                    this.add(new Clarke());
//                    this.add(new GolemAssault());
//                    this.add(new Kyouka(0));
//                    this.add(new SorceryInSolidarity());
//                    this.add(new Isabelle());
//                    this.add(new Maiser());
//                    this.add(new Vincent());
//                    this.add(new TheFool());
//                }
//            });
//            //增幅
//            witchCardGroupPool.add(new ArrayList<AbstractCard>(){
//                {
//                    this.add(new FatesHand());
//                    this.add(new MagicOwl());
//                    this.add(new TruthsAdjudication());
//                    this.add(new ZealotOfTruth());
//                    this.add(new DimensionShift());
//                    this.add(new EdictOfTruth());
//                    this.add(new FireChain());
//                    this.add(new GrimoireSorcerer());
//                    this.add(new DimensionalWitch());
//                    this.add(new Ghios());
//                    this.add(new GigantChimera());
//                    this.add(new Kuon());
//                    this.add(new Runie());
//                }
//            });
//            //土法
//            witchCardGroupPool.add(new ArrayList<AbstractCard>(){
//                {
//                    this.add(new CommenceExperiment());
//                    this.add(new DwarfAlchemist());
//                    this.add(new JubilanceWitch());
//                    this.add(new WitchSnap());
//                    this.add(new AcidGolem());
//                    this.add(new Cagliostro());
//                    this.add(new GrandSummoning());
//                    this.add(new PiousInstructor());
//                    this.add(new MasterMageLevi());
//                    this.add(new Telescope());
//                    this.add(new WitchsCauldron());
//                    this.add(new ArcticChimera());
//                    this.add(new Erasmus());
//                    this.add(new ForbiddenDarkMage());
//                    this.add(new Magisa());
//                    this.add(new OrichalcumGolem());
//                    this.add(new Faust());
//                }
//            });
//            //自然机械
//            witchCardGroupPool.add(new ArrayList<AbstractCard>(){
//                {
//                    this.add(new Aeroelementalist());
//                    this.add(new Elementalmana());
//                    this.add(new Geoelementist());
//                    this.add(new MechanizedLifeform());
//                    this.add(new MechastaffSorcerer());
//                    this.add(new MagitechGolem());
//                    this.add(new MechabookSorcerer());
//                    this.add(new Pyromancer());
//                    this.add(new Riley());
//                    this.add(new ApexElemental());
//                    this.add(new Tetra());
//                }
//            });
//            //马纳历亚
//            witchCardGroupPool.add(new ArrayList<AbstractCard>(){
//                {
//                    this.add(new Lou());
//                    this.add(new MysterianWisdom());
//                    this.add(new Ogler());
//                    this.add(new Owen());
//                    this.add(new Hanna());
//                    this.add(new Anne());
//                    this.add(new Craig());
//                    this.add(new Grea());
//                    this.add(new Miranda());
//                }
//            });
//            //象棋
//            witchCardGroupPool.add(new ArrayList<AbstractCard>(){
//                {
//                    this.add(new MagicalRook());
//                    this.add(new MagicalKnight());
//                    this.add(new Check());
//                    this.add(new MagicalStrategy());
//                    this.add(new MysticQueen());
//                    this.add(new Blitz());
//                    this.add(new MysticKing());
//                }
//            });
//            //其他
//            witchCardGroupPool.add(new ArrayList<AbstractCard>(){
//                {
//                    this.add(new AdherentOfAnnihilation());
//                    this.add(new Shop());
//                    this.add(new UnleashTruth());
//                    this.add(new GuildAssembly());
//                    this.add(new ImaginationRealized());
//                    this.add(new MadcapConjuration());
//                    this.add(new Lifetime());
//                    this.add(new Chaos());
//                    this.add(new Awakened());
//                    this.add(new MysticSeeker());
//                    this.add(new OmenOfTruth());
//                    this.add(new Oz());
//                    this.add(new Yukishima());
//                }
//            });
        }
    }
}
