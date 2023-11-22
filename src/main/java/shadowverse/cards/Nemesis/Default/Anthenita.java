package shadowverse.cards.Nemesis.Default;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;
import shadowverse.stance.Resonance;

public class Anthenita extends CustomCard {
    public static final String ID = "shadowverse:Anthenita";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Anthenita");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Anthenita.png";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("DualWieldAction").TEXT;

    public Anthenita() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Anthenita"));
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, false, card -> card.color != Nemesis.Enums.COLOR_SKY, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                AbstractCard tmp = c.makeStatEquivalentCopy();
                addToBot(new MakeTempCardInHandAction(tmp));
                addToBot(new MakeTempCardInDrawPileAction(tmp,1,true,true));
            }
        }));
        if (upgraded){
            if ((p.drawPile.size()+1)%2==0&&!p.stance.ID.equals(Resonance.STANCE_ID)){
                addToBot(new ChangeStanceAction(new Resonance()));
                if (p instanceof AbstractShadowversePlayer)
                    ((AbstractShadowversePlayer) p).resonanceCount++;
            }else if ((p.drawPile.size()+1)%2!=0&& p.stance.ID.equals(Resonance.STANCE_ID)){
                addToBot(new ChangeStanceAction(new NeutralStance()));
                if (p instanceof AbstractShadowversePlayer)
                    ((AbstractShadowversePlayer) p).resonanceCount--;
            }
            addToBot(new SelectCardsInHandAction(1, TEXT[0], false, false, card -> card.color == Nemesis.Enums.COLOR_SKY, abstractCards -> {
                for (AbstractCard c : abstractCards) {
                    AbstractCard tmp = c.makeStatEquivalentCopy();
                    addToBot(new MakeTempCardInHandAction(tmp));
                    addToBot(new MakeTempCardInDrawPileAction(tmp,1,true,true));
                }
            }));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Anthenita();
    }
}
