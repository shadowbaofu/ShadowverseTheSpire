package shadowverse.cards.Nemesis.Artifact;


import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;
import shadowverse.powers.Cemetery;

import java.util.ArrayList;
import java.util.Collections;


public class Zerk extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Zerk";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Zerk");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Zerk.png";
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("ExhaustAction")).TEXT;

    public Zerk() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.SELF, 0, CardType.SKILL);
        this.baseBlock = 9;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Zerk"));
        addToBot(new GainBlockAction(p, p, this.block));
        ArrayList<AbstractCard> list = new ArrayList<>();
        ArrayList<String> dup = new ArrayList<>();
        for (AbstractCard c : p.exhaustPile.group) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT) && !dup.contains(c.cardID)) {
                dup.add(c.cardID);
                AbstractCard card = c.makeCopy();
                list.add(card);
            }
        }
        if (list.size() > 0) {
            Collections.shuffle(list);
            for (AbstractCard ca : list) {
                ca.setCostForTurn(0);
            }
            addToBot(new MakeTempCardInHandAction(list.get(0)));
            if (list.size() > 1)
                addToBot(new MakeTempCardInHandAction(list.get(1)));
            if (list.size() > 2)
                addToBot(new MakeTempCardInHandAction(list.get(2)));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Zerk_Acc"));
        addToBot(new SelectCardsAction(p.discardPile.group, TEXT[0], false, card -> {
            return card.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT);
        }, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
                addToBot(new DrawCardAction(1));
                addToBot(new HealAction(p, p, 2));
                addToBot(new ApplyPowerAction(p, p, new Cemetery(p, 1), 1));
            }
        }));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new Zerk();
    }
}
