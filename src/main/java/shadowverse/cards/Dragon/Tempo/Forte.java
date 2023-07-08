package shadowverse.cards.Dragon.Tempo;


import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.characters.Dragon;
import shadowverse.powers.Forte4Power;
import shadowverse.powers.OverflowPower;

import java.util.ArrayList;
import java.util.List;

public class Forte extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Forte";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Forte");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Forte2");
    public static CardStrings cardStrings3 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Forte3");
    public static CardStrings cardStrings4 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Forte4");
    public static CardStrings cardStrings5 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Forte5");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Forte.png";
    public static final String IMG_PATH2 = "img/cards/Forte2.png";
    public static final String IMG_PATH3 = "img/cards/Forte3.png";
    public static final String IMG_PATH4 = "img/cards/Forte4.png";
    public static final String IMG_PATH5 = "img/cards/Forte5.png";

    private boolean trigger;

    public Forte() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 25;
    }



    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("Forte"));
                addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 1:
                addToBot(new SFXAction("Forte2"));
                addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new StrengthPower(abstractPlayer,3),3));
                addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DexterityPower(abstractPlayer,1),1));
                break;
            case 2:
                addToBot(new SFXAction("Forte3"));
                addToBot(new GainBlockAction(abstractPlayer,this.block));
                addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new VulnerablePower(abstractMonster,2,false),2));
                addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new WeakPower(abstractMonster,2,false),2));
                break;
            case 3:
                addToBot(new SFXAction("Forte4"));
                addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new Forte4Power(abstractPlayer)));
                break;
            case 4:
                addToBot(new SFXAction("Forte5"));
                addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                if (abstractPlayer.hasPower(OverflowPower.POWER_ID)){
                    TwoAmountPower p = (TwoAmountPower) abstractPlayer.getPower(OverflowPower.POWER_ID);
                    if (p.amount2 > 0){
                        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                    }
                }
                if (!trigger){
                    trigger = true;
                    addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new StrengthPower(abstractPlayer,2),2));
                    addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DexterityPower(abstractPlayer,2),2));
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Forte();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Forte.this.timesUpgraded;
                Forte.this.upgraded = true;
                Forte.this.name = NAME + "+";
                Forte.this.initializeTitle();
                Forte.this.baseDamage = 35;
                Forte.this.upgradedDamage = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Forte.this.timesUpgraded;
                Forte.this.upgraded = true;
                Forte.this.textureImg = IMG_PATH2;
                Forte.this.loadCardImage(IMG_PATH2);
                Forte.this.name = cardStrings2.NAME;
                Forte.this.initializeTitle();
                Forte.this.rawDescription = cardStrings2.DESCRIPTION;
                Forte.this.initializeDescription();
                Forte.this.upgradeBaseCost(3);
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Forte.this.timesUpgraded;
                Forte.this.upgraded = true;
                Forte.this.textureImg = IMG_PATH3;
                Forte.this.loadCardImage(IMG_PATH3);
                Forte.this.name = cardStrings3.NAME;
                Forte.this.initializeTitle();
                Forte.this.rawDescription = cardStrings3.DESCRIPTION;
                Forte.this.initializeDescription();
                Forte.this.upgradeBaseCost(4);
                Forte.this.baseBlock = 45;
                Forte.this.upgradedBlock = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Forte.this.timesUpgraded;
                Forte.this.upgraded = true;
                Forte.this.textureImg = IMG_PATH4;
                Forte.this.loadCardImage(IMG_PATH4);
                Forte.this.name = cardStrings4.NAME;
                Forte.this.initializeTitle();
                Forte.this.rawDescription = cardStrings4.DESCRIPTION;
                Forte.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Forte.this.timesUpgraded;
                Forte.this.baseDamage = 16;
                Forte.this.upgradedDamage = true;
                Forte.this.upgraded = true;
                Forte.this.textureImg = IMG_PATH5;
                Forte.this.loadCardImage(IMG_PATH5);
                Forte.this.name = cardStrings5.NAME;
                Forte.this.initializeTitle();
                Forte.this.rawDescription = cardStrings5.DESCRIPTION;
                Forte.this.initializeDescription();
            }
        });
        return list;
    }
}

