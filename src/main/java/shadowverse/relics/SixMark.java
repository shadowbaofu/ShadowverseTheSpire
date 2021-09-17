 package shadowverse.relics;


 import basemod.abstracts.CustomRelic;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.powers.*;
 import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;
 import com.megacrit.cardcrawl.relics.AbstractRelic;

 import java.util.ArrayList;
 import java.util.Collections;


 public class SixMark
   extends CustomRelic
 {
   public static final String ID = "shadowverse:SixMark";
   public static final String IMG = "img/relics/SixMark.png";
   public static final String OUTLINE_IMG = "img/relics/outline/SixMark_Outline.png";
   
   private static  ArrayList<Integer> randomDebuff() {
     ArrayList<Integer> list = new ArrayList<>();
     for (int i=0;i<6;i++){
       list.add(i);
     }
     return list;
   }

   private ArrayList<Integer> rand = randomDebuff();
   
   public SixMark() {
     super(ID, ImageMaster.loadImage(IMG), RelicTier.RARE, LandingSound.CLINK);
   }
   
   public String getUpdatedDescription() {
     return this.DESCRIPTIONS[0];
   }
   
   public void atBattleStart() {
     this.counter = 0;
     addToBot((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
   }
   
   public void atTurnStart() {
     if (!this.grayscale) {
       this.counter++;
       if (rand.size()>0){
         Collections.shuffle(rand);
         switch (rand.get(0)){
           case 0:
             flash();
             for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
               addToBot((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)mo, this));
               addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new VulnerablePower((AbstractCreature)mo, 1, false), 1, true));
             }
             break;
           case 1:
             flash();
             for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
               addToBot((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)mo, this));
               addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new WeakPower((AbstractCreature)mo, 1, false), 1, true));
             }
             break;
           case 2:
             flash();
             for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
               addToBot((AbstractGameAction) new RelicAboveCreatureAction((AbstractCreature) mo, this));
               addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) mo, (AbstractCreature) AbstractDungeon.player, (AbstractPower) new PoisonPower((AbstractCreature) mo, AbstractDungeon.player, 1), 1, true));
             }
             break;
           case 3:
             flash();
             for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
               addToBot((AbstractGameAction) new RelicAboveCreatureAction((AbstractCreature) mo, this));
               addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new StrengthPower((AbstractCreature)mo, -1), -1));
               if (!mo.hasPower("Artifact"))
                 addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new GainStrengthPower((AbstractCreature)mo, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
             }
             break;
           case 4:
             flash();
             for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
               addToBot((AbstractGameAction) new RelicAboveCreatureAction((AbstractCreature) mo, this));
               addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new BlockReturnPower((AbstractCreature)mo, 1), 1));
             }
             break;
           case 5:
             flash();
             for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
               addToBot((AbstractGameAction) new RelicAboveCreatureAction((AbstractCreature) mo, this));
               addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new ChokePower((AbstractCreature)mo, 1), 1));
             }
             break;
           default:
             break;
         }
         rand.remove(0);
       }
     } 
     if (this.counter == 6) {
       flash();
       this.counter = -1;
       this.grayscale = true;
     } 
   }
 
   
   public void onVictory() {
     this.counter = -1;
     this.grayscale = false;
     rand = randomDebuff();
   }
 
   
   public AbstractRelic makeCopy() {
     return (AbstractRelic)new SixMark();
   }
 }


