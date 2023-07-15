package shadowverse.cards.Nemesis.Artifact;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.ChoiceAction2;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.characters.Nemesis;

import java.util.ArrayList;


public class SisterlyBonds
        extends CustomCard {
    public static final String ID = "shadowverse:SisterlyBonds";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SisterlyBonds");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SisterlyBonds.png";
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new SpineArtifact());
        list.add(new SpineLucille());
        return list;
    }

    public SisterlyBonds() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.NONE);
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
            } else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("SisterlyBonds"));
        AbstractCard s = new SpineArtifact();
        AbstractCard sl = new SpineLucille();
        addToBot(new ChoiceAction2(s,sl));
    }


    public AbstractCard makeCopy() {
        return new SisterlyBonds();
    }
}

