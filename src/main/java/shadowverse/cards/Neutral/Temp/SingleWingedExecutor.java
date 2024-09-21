package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SingleWingedExecutor extends CustomCard {
    public static final String ID = "shadowverse:SingleWingedExecutor";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SingleWingedExecutor.png";
    private boolean triggered;

    public SingleWingedExecutor() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 6;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainEnergyAction(1));
        addToBot(new GainBlockAction(abstractPlayer,this.block));
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.color == CardColor.COLORLESS && c.type == CardType.ATTACK && !triggered){
            c.upgrade();
            this.upgrade();
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,1),1));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,1),1));
            triggered = true;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SingleWingedExecutor();
    }
}


