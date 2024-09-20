package shadowverse.cards.Royal.Default;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Royal;
import shadowverse.relics.KagemitsuSword;

public class Ilmisuna extends CustomCard {
    public static final String ID = "shadowverse:Ilmisuna";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Ilmisuna.png";
    public static final String IMG_PATH_EV = "img/cards/Ilmisuna_Ev.png";

    public Ilmisuna() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 6;
        this.tags.add(AbstractShadowversePlayer.Enums.EVOLVEABLE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.textureImg = IMG_PATH_EV;
            this.loadCardImage(IMG_PATH_EV);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void degrade() {
        if (this.upgraded) {
            degradeName();
            this.textureImg = IMG_PATH;
            this.loadCardImage(IMG_PATH);
            this.rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
            this.superFlash();
            this.applyPowers();
        }
    }

    public void degradeName() {
        --this.timesUpgraded;
        this.upgraded = false;
        this.name = NAME;
        this.initializeTitle();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new SFXAction(ID.replace("shadowverse:", "") + "_Ev"));
        } else {
            addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        }
        addToBot(new GainBlockAction(p, p, this.block));
        if (this.upgraded) {
            addToBot(new SelectCardsInHandAction(1, TEXT[0], false, false, card -> true, abstractCards -> {
                for (AbstractCard c : abstractCards) {
                    addToBot(new ExhaustSpecificCardAction(c, p.hand));
                    addToBot(new DrawCardAction(2));
                }
            }));
            this.degrade();
            if (p.hasRelic(KagemitsuSword.ID) || p.hasPower("shadowverse:SeofonPower")) {
                this.upgrade();
            }
        }
    }

    @Override
    public void triggerWhenDrawn() {
        if (!this.upgraded) {
            this.addToTop(new MakeTempCardInHandAction(new EvolutionPoint(), 1));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new Ilmisuna();
    }
}

