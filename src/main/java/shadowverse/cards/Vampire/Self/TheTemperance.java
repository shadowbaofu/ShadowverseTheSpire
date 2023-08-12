package shadowverse.cards.Vampire.Self;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.Vampire;
import shadowverse.powers.*;

import java.util.ArrayList;


public class TheTemperance
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:TheTemperance";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:TheTemperance");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/TheTemperance.png";
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new InsatiableDesire());
        return list;
    }

    public TheTemperance() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.POWER, Vampire.Enums.COLOR_SCARLET, CardRarity.RARE, CardTarget.SELF, 0, CardType.SKILL);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void update() {
        super.update();
        if (this.hb.hovered)
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = 2.0F;
                this.cardsToPreview = returnChoice().get(previewIndex).makeCopy();
                if (this.previewIndex == returnChoice().size() - 1) {
                    this.previewIndex = 0;
                } else {
                    this.previewIndex++;
                }
                if (this.upgraded)
                    this.cardsToPreview.upgrade();
            } else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("TheTemperance"));
        addToBot(new ApplyPowerAction(p, p, new TheTemperancePower(p, this.magicNumber, 0), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1)));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("TheTemperance_Acc"));
        if (p.hasPower(AvaricePower.POWER_ID) || p.hasPower(EpitaphPower.POWER_ID)) {
            AbstractCard i = new InsatiableDesire();
            if (this.upgraded)
                i.upgrade();
            addToBot(new MakeTempCardInHandAction(i));
        } else {
            AbstractCard u = new UnselfishGrace();
            if (this.upgraded)
                u.upgrade();
            addToBot(new MakeTempCardInHandAction(u));
        }
    }


    public AbstractCard makeCopy() {
        return new TheTemperance();
    }
}


