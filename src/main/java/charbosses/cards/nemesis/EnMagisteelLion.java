package charbosses.cards.nemesis;

import charbosses.actions.common.EnemyMakeTempCardInDrawPileAction;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyFlameBarrierPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import shadowverse.cards.Temp.LionBless;
import shadowverse.characters.Nemesis;

public class EnMagisteelLion extends AbstractBossCard {
    public static final String ID = "shadowverse:EnMagisteelLion";

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:EnMagisteelLion");

    public static final String IMG_PATH = "img/cards/MagisteelLion.png";

    public EnMagisteelLion() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseBlock = 12;
        this.cardsToPreview = new EnAnalyzeArtifact();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)m, (AbstractCreature)m, this.block));
        addToBot((AbstractGameAction)new EnemyMakeTempCardInDrawPileAction(this.cardsToPreview.makeStatEquivalentCopy(),2,true,true,false));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(6);
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard)new EnMagisteelLion();
    }
}
