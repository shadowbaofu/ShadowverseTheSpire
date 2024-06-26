package shadowverse.cards.Bishop.Default;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.characters.Bishop;
import shadowverse.powers.DisableEffectDamagePower;

import java.util.ArrayList;
import java.util.List;


public class Jeanne extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Jeanne";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Jeanne");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Jeanne2");
    public static CardStrings cardStrings3 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Jeanne3");
    public static CardStrings cardStrings4 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Jeanne4");
    public static CardStrings cardStrings5 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Jeanne5");
    public static CardStrings cardStrings6 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Jeanne6");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Jeanne.png";
    public static final String IMG_PATH2 = "img/cards/Jeanne2.png";
    public static final String IMG_PATH3 = "img/cards/Jeanne3.png";
    public static final String IMG_PATH4 = "img/cards/Jeanne4.png";
    public static final String IMG_PATH5 = "img/cards/Jeanne5.png";
    public static final String IMG_PATH6 = "img/cards/Jeanne6.png";


    public Jeanne() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.RARE, CardTarget.ALL);
        this.baseDamage = 16;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.isMultiDamage = true;
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("Jeanne"));
                addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,this.magicNumber),this.magicNumber));
                break;
            case 1:
                addToBot(new SFXAction("Jeanne2"));
                addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,this.magicNumber),this.magicNumber));
                break;
            case 2:
                addToBot(new SFXAction("Jeanne3"));
                addToBot(new HealAction(p,p,this.magicNumber));
                break;
            case 3:
                addToBot(new SFXAction("Jeanne4"));
                addToBot(new LoseHPAction(p,p,this.magicNumber));
                addToBot(new ApplyPowerAction(p,p,new RegenPower(p,2),2));
                break;
            case 4:
                addToBot(new DrawCardAction(1));
                addToBot(new SFXAction("ATTACK_HEAVY"));
                addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
                addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                addToBot(new SFXAction("Jeanne5"));
                addToBot(new HealAction(p,p,this.magicNumber));
                addToBot(new HealAction(p,p,this.magicNumber));
                break;
            case 5:
                addToBot(new SFXAction("Jeanne6"));
                addToBot(new GainEnergyAction(2));
                addToBot(new ArmamentsAction(true));
                addToBot(new ApplyPowerAction(p,p,new DisableEffectDamagePower(p,1),1));
                if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() > 2){
                    addToBot(new GainBlockAction(p,this.block));
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Jeanne();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Jeanne.this.timesUpgraded;
                Jeanne.this.upgraded = true;
                Jeanne.this.name = cardStrings.NAME + "+";
                Jeanne.this.initializeTitle();
                Jeanne.this.baseDamage = 21;
                Jeanne.this.upgradedDamage = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Jeanne.this.timesUpgraded;
                Jeanne.this.upgraded = true;
                Jeanne.this.textureImg = IMG_PATH2;
                Jeanne.this.loadCardImage(IMG_PATH2);
                Jeanne.this.name = cardStrings2.NAME;
                Jeanne.this.initializeTitle();
                Jeanne.this.baseDamage = 21;
                Jeanne.this.upgradedDamage = true;
                Jeanne.this.rawDescription = cardStrings2.DESCRIPTION;
                Jeanne.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Jeanne.this.timesUpgraded;
                Jeanne.this.upgraded = true;
                Jeanne.this.textureImg = IMG_PATH3;
                Jeanne.this.loadCardImage(IMG_PATH3);
                Jeanne.this.name = cardStrings3.NAME;
                Jeanne.this.baseDamage = 27;
                Jeanne.this.upgradedDamage = true;
                Jeanne.this.baseMagicNumber = 3;
                Jeanne.this.magicNumber = Jeanne.this.baseMagicNumber;
                Jeanne.this.initializeTitle();
                Jeanne.this.rawDescription = cardStrings3.DESCRIPTION;
                Jeanne.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Jeanne.this.timesUpgraded;
                Jeanne.this.upgraded = true;
                Jeanne.this.textureImg = IMG_PATH4;
                Jeanne.this.loadCardImage(IMG_PATH4);
                Jeanne.this.name = cardStrings4.NAME;
                Jeanne.this.baseDamage = 35;
                Jeanne.this.upgradedDamage = true;
                Jeanne.this.baseMagicNumber = 5;
                Jeanne.this.magicNumber = Jeanne.this.baseMagicNumber;
                Jeanne.this.initializeTitle();
                Jeanne.this.rawDescription = cardStrings4.DESCRIPTION;
                Jeanne.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Jeanne.this.timesUpgraded;
                Jeanne.this.upgraded = true;
                Jeanne.this.textureImg = IMG_PATH5;
                Jeanne.this.loadCardImage(IMG_PATH5);
                Jeanne.this.name = cardStrings5.NAME;
                Jeanne.this.baseDamage = 12;
                Jeanne.this.upgradedDamage = true;
                Jeanne.this.baseMagicNumber = 2;
                Jeanne.this.magicNumber = Jeanne.this.baseMagicNumber;
                Jeanne.this.initializeTitle();
                Jeanne.this.rawDescription = cardStrings5.DESCRIPTION;
                Jeanne.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Jeanne.this.timesUpgraded;
                Jeanne.this.upgraded = true;
                Jeanne.this.textureImg = IMG_PATH6;
                Jeanne.this.loadCardImage(IMG_PATH6);
                Jeanne.this.name = cardStrings6.NAME;
                Jeanne.this.baseDamage = 15;
                Jeanne.this.upgradedDamage = true;
                Jeanne.this.baseBlock = 15;
                Jeanne.this.upgradedBlock = true;
                Jeanne.this.initializeTitle();
                Jeanne.this.rawDescription = cardStrings6.DESCRIPTION;
                Jeanne.this.initializeDescription();
            }
        });
        return list;
    }
}

