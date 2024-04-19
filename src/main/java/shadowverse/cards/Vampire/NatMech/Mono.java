package shadowverse.cards.Vampire.NatMech;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.FirstOne;
import shadowverse.cards.Neutral.Temp.GarnetRelease;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Vampire;
import shadowverse.powers.EpitaphPower;
import shadowverse.powers.MonoPower;
import shadowverse.stance.Vengeance;

import java.util.ArrayList;
import java.util.List;


public class Mono
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Mono";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Mono");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Mono2");
    public static CardStrings cardStrings3 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Mono3");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Mono.png";
    public static final String IMG_PATH2 = "img/cards/Mono2.png";
    private int previewBranch;
    private static AbstractCard firstOne = new FirstOne();
    private static AbstractCard upgradedFirstOne(){
        AbstractCard c = new FirstOne();
        c.upgrade();
        return c;
    }

    private static AbstractCard garnetRelease = new GarnetRelease();

    public boolean released;

    public Mono() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Vampire.Enums.COLOR_SCARLET, CardRarity.RARE, CardTarget.ENEMY);
        this.baseBlock = 6;
        this.cardsToPreview =  new FirstOne();
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
    }


    public void upgrade() {
        ((UpgradeBranch)((BranchableUpgradeCard)this).possibleBranches().get(chosenBranch())).upgrade();
    }

    public void update() {
        super.update();
        if (this.previewBranch == 0) {
            if (this.upgraded) {
                this.cardsToPreview = upgradedFirstOne();
            } else
                this.cardsToPreview = firstOne;
        }else if (this.previewBranch == 1){
            this.cardsToPreview = garnetRelease;
        }
    }


    public void applyPowers() {
        if (chosenBranch()==0){
            super.applyPowers();
            int count = 0;
            for (AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisCombat){
                if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE)&&c.type!=CardType.SKILL){
                    count++;
                }
            }
            this.rawDescription = cardStrings.DESCRIPTION;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }else if (previewBranch == 2){
            if (released){
                this.baseDamage = 36;
                this.rawDescription = cardStrings3.EXTENDED_DESCRIPTION[0];
                this.initializeDescription();
            }else {
                super.applyPowers();
            }
        }else {
            super.applyPowers();
        }
    }

    @Override
    public void atTurnStart(){
        if (chosenBranch()==1)
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,(AbstractPower)new MonoPower(AbstractDungeon.player,0)));
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                addToBot(new SFXAction("Mono"));
                addToBot(new GainBlockAction(abstractPlayer, this.block));
                int mCount = 0;
                for (AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisCombat){
                    if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE)&&c.type!=CardType.SKILL){
                        mCount++;
                    }
                }
                if (mCount>=7){
                    AbstractCard ca = null;
                    if (this.upgraded){
                        ca =upgradedFirstOne().makeStatEquivalentCopy();
                    }else {
                        ca =firstOne.makeStatEquivalentCopy();
                    }
                    addToBot(new MakeTempCardInHandAction(ca));
                }
                break;
            case 1:
                addToBot(new SFXAction("Mono2"));
                addToBot(new GainBlockAction(abstractPlayer, this.block));
                if (abstractPlayer.hasPower(EpitaphPower.POWER_ID)||abstractPlayer.stance.ID.equals(Vengeance.STANCE_ID)){
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DexterityPower(abstractPlayer, 1), 1));
                }
                int pCount = 0;
                AbstractPower monoPower = null;
                for (AbstractPower power:abstractPlayer.powers){
                    if (power instanceof MonoPower)
                        monoPower = power;
                }
                if (monoPower!=null&&monoPower.amount>=3||(monoPower!=null&&monoPower.amount>=2&&(abstractPlayer.hasPower(EpitaphPower.POWER_ID)||abstractPlayer.stance.ID.equals(Vengeance.STANCE_ID)))){
                    addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    addToBot(new ArmamentsAction(true));
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DoubleDamagePower(abstractPlayer, 1, false), 1));
                }
                break;
            case 2:
                addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                if (released){
                    addToBot(new SFXAction("Mono3_A"));
                    int count = 0;
                    for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                        if (c instanceof EvolutionPoint)
                            count++;
                    }
                    if (count > 9){
                        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DoubleDamagePower(abstractPlayer, 1, false), 1));
                    }
                }else {
                    addToBot(new SFXAction("Mono3"));
                    int count = 0;
                    for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                        if (c instanceof EvolutionPoint)
                            count++;
                    }
                    if (count > 4){
                        addToBot(new MakeTempCardInHandAction(new GarnetRelease()));
                    }
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return  new Mono();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Mono.this.timesUpgraded;
                Mono.this.upgraded = true;
                Mono.this.name = cardStrings.NAME + "+";
                Mono.this.initializeTitle();
                Mono.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                Mono.this.initializeDescription();
                Mono.this.previewBranch = 0;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Mono.this.timesUpgraded;
                Mono.this.upgraded = true;
                Mono.this.textureImg = IMG_PATH2;
                Mono.this.loadCardImage(IMG_PATH2);
                Mono.this.name = cardStrings2.NAME;
                Mono.this.initializeTitle();
                Mono.this.rawDescription = cardStrings2.DESCRIPTION;
                Mono.this.initializeDescription();
                Mono.this.previewBranch = 1;
                Mono.this.baseDamage = 36;
                Mono.this.upgradedDamage = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Mono.this.timesUpgraded;
                Mono.this.upgraded = true;
                Mono.this.name = cardStrings3.NAME;
                Mono.this.initializeTitle();
                Mono.this.rawDescription = cardStrings3.DESCRIPTION;
                Mono.this.initializeDescription();
                Mono.this.previewBranch = 2;
                Mono.this.baseDamage = 6;
                Mono.this.upgradedDamage = true;
            }
        });
        return list;
    }
}


