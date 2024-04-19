package shadowverse.cards.Neutral.Neutral;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.cards.Neutral.Temp.NaterranGreatTree;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.ArrayList;
import java.util.List;

public class Mother extends AbstractNeutralCard implements BranchableUpgradeCard {
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Mother");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Mother2");
    public static final String ID = "shadowverse:Mother";
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION; public static final String IMG_PATH = "img/cards/Mother.png";
    public Mother() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY);
        this.baseMagicNumber = 7;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = 20;
        this.tags.add(AbstractShadowversePlayer.Enums.NATURAL);
    }

    @Override
    public void upgrade() {
        ((UpgradeBranch)((BranchableUpgradeCard)this).possibleBranches().get(chosenBranch())).upgrade();
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer){
            int count = ((AbstractShadowversePlayer) AbstractDungeon.player).naterranCount;
            this.rawDescription = chosenBranch()==0?cardStrings.DESCRIPTION:cardStrings2.DESCRIPTION;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                addToBot(new SFXAction("Mother"));
                addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new PoisonPower(abstractMonster, abstractPlayer, this.magicNumber)));
                if (AbstractDungeon.player instanceof AbstractShadowversePlayer){
                    if (((AbstractShadowversePlayer)abstractPlayer).naterranCount >= 1){
                        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new PoisonPower(abstractMonster, abstractPlayer, this.magicNumber)));
                    }
                    if (((AbstractShadowversePlayer)abstractPlayer).naterranCount >= 3){
                        addToBot(new GainBlockAction(abstractPlayer,this.block));
                    }
                }
                break;
            case 1:
                addToBot(new SFXAction("Mother2"));
                addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new PoisonPower(abstractMonster, abstractPlayer, this.magicNumber)));
                addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new PoisonPower(abstractMonster, abstractPlayer, this.magicNumber)));
                addToBot(new MakeTempCardInHandAction(new NaterranGreatTree(),2));
                if (AbstractDungeon.player instanceof AbstractShadowversePlayer){
                    if (((AbstractShadowversePlayer)abstractPlayer).naterranCount >= 5){
                        addToBot(new GainBlockAction(abstractPlayer,this.block));
                        addToBot(new HealAction(abstractPlayer,abstractPlayer,4));
                    }
                }
                break;
        }
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Mother.this.timesUpgraded;
                Mother.this.upgraded = true;
                Mother.this.name = cardStrings.NAME + "+";
                Mother.this.initializeTitle();
                Mother.this.baseMagicNumber = 9;
                Mother.this.magicNumber = Mother.this.baseMagicNumber;
                Mother.this.upgradedMagicNumber = true;
                Mother.this.baseBlock = 23;
                Mother.this.upgradedBlock = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Mother.this.timesUpgraded;
                Mother.this.upgraded = true;
                Mother.this.name = cardStrings2.NAME;
                Mother.this.initializeTitle();
                Mother.this.rawDescription = cardStrings2.DESCRIPTION;
                Mother.this.initializeDescription();
                Mother.this.baseBlock = 16;
                Mother.this.upgradedBlock = true;
            }
        });
        return list;
    }
}
