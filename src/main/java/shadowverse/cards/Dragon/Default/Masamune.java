package shadowverse.cards.Dragon.Default;


import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.characters.Dragon;
import shadowverse.powers.Masamune2Power;
import shadowverse.powers.OverflowPower;

import java.util.ArrayList;
import java.util.List;

public class Masamune extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Masamune";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Masamune");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Masamune2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Masamune.png";
    public static final String IMG_PATH2 = "img/cards/Masamune2.png";

    public Masamune() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 8;
        ExhaustiveVariable.setBaseValue(this, 3);
    }

    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("Masamune"));
                addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                if (abstractPlayer.hasPower(OverflowPower.POWER_ID)){
                    TwoAmountPower p = (TwoAmountPower) abstractPlayer.getPower(OverflowPower.POWER_ID);
                    if (p.amount2 > 1){
                        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BufferPower(abstractPlayer,1),1));
                    }
                }
                break;
            case 1:
                addToBot(new SFXAction("Masamune2"));
                addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                if (abstractPlayer.hasPower(OverflowPower.POWER_ID)){
                    TwoAmountPower p = (TwoAmountPower) abstractPlayer.getPower(OverflowPower.POWER_ID);
                    if (p.amount2 > 0){
                        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BufferPower(abstractPlayer,1),1));
                        addToBot(new MakeTempCardInHandAction(new EvolutionPoint()));
                    }
                    if (p.amount2 > 1){
                        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new Masamune2Power(abstractPlayer)));
                    }
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Masamune();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Masamune.this.timesUpgraded;
                Masamune.this.upgraded = true;
                Masamune.this.name = NAME + "+";
                Masamune.this.initializeTitle();
                Masamune.this.baseDamage = 12;
                Masamune.this.upgradedDamage = true;
                ExhaustiveVariable.setBaseValue(Masamune.this, 5);
                ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(Masamune.this, true);
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Masamune.this.timesUpgraded;
                Masamune.this.upgraded = true;
                Masamune.this.textureImg = IMG_PATH2;
                Masamune.this.loadCardImage(IMG_PATH2);
                Masamune.this.name = cardStrings2.NAME;
                Masamune.this.initializeTitle();
                Masamune.this.rawDescription = cardStrings2.DESCRIPTION;
                Masamune.this.initializeDescription();
                Masamune.this.upgraded = true;
            }
        });
        return list;
    }
}

