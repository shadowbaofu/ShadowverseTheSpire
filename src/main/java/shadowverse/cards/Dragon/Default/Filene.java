package shadowverse.cards.Dragon.Default;


import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.WhitefrostWhisper;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;
import shadowverse.powers.OverflowPower;

import java.util.ArrayList;
import java.util.List;

public class Filene extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Filene";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Filene");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Filene2");
    public static CardStrings cardStrings3 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Filene3");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Filene.png";
    public static final String IMG_PATH2 = "img/cards/Filene2.png";
    public static final String IMG_PATH3 = "img/cards/Filene3.png";
    private boolean trigger;

    public Filene() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 6;
        this.cardsToPreview = new WhitefrostWhisper();
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("Filene"));
                addToBot(new GainBlockAction(abstractPlayer,this.block));
                addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                break;
            case 1:
                addToBot(new SFXAction("Filene2"));
                addToBot(new GainBlockAction(abstractPlayer,this.block));
                addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                break;
            case 2:
                addToBot(new SFXAction("Filene3"));
                addToBot(new GainBlockAction(abstractPlayer,this.block));
                addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                if (abstractPlayer.hasPower(OverflowPower.POWER_ID) && !trigger){
                    TwoAmountPower p = (TwoAmountPower) abstractPlayer.getPower(OverflowPower.POWER_ID);
                    if (p.amount2 > 0){
                        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                            if (m != null && !m.isDeadOrEscaped()){
                                addToBot(new StunMonsterAction(m,abstractPlayer));
                            }
                        }
                        trigger = true;
                    }
                }
                break;
        }
    }

    @Override
    public void triggerOnExhaust() {
        if (this.hasTag(AbstractShadowversePlayer.Enums.LASTWORD)){
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new OverflowPower(AbstractDungeon.player,1)));
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new Filene();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Filene.this.timesUpgraded;
                Filene.this.upgraded = true;
                Filene.this.name = NAME + "+";
                Filene.this.initializeTitle();
                Filene.this.baseBlock = 9;
                Filene.this.upgradedBlock = true;
                Filene.this.cardsToPreview.upgrade();
                Filene.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                Filene.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Filene.this.timesUpgraded;
                Filene.this.textureImg = IMG_PATH2;
                Filene.this.loadCardImage(IMG_PATH2);
                Filene.this.name = cardStrings2.NAME;
                Filene.this.initializeTitle();
                Filene.this.rawDescription = cardStrings2.DESCRIPTION;
                Filene.this.initializeDescription();
                Filene.this.upgraded = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Filene.this.timesUpgraded;
                Filene.this.textureImg = IMG_PATH3;
                Filene.this.loadCardImage(IMG_PATH3);
                Filene.this.name = cardStrings3.NAME;
                Filene.this.initializeTitle();
                Filene.this.rawDescription = cardStrings3.DESCRIPTION;
                Filene.this.initializeDescription();
                Filene.this.upgraded = true;
            }
        });
        return list;
    }
}

