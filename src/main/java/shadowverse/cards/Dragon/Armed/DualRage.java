package shadowverse.cards.Dragon.Armed;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.ChoiceAction;
import shadowverse.action.DualRageAction;
import shadowverse.cards.Neutral.Temp.DragonWeapon;
import shadowverse.cards.Neutral.Temp.DualModeA;
import shadowverse.cards.Neutral.Temp.DualModeB;
import shadowverse.cards.Neutral.Temp.DualModeC;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;

import java.util.ArrayList;


public class DualRage extends CustomCard {
    public static final String ID = "shadowverse:DualRage";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DualRage");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DualRage.png";
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new DragonWeapon());
        list.add(new DualModeA());
        list.add(new DualModeB());
        list.add(new DualModeC());
        return list;
    }

    public DualRage() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.SKILL, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.NONE);
        this.tags.add(AbstractShadowversePlayer.Enums.ARMED);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
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



    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int count = 0;
        boolean hasLavatin = false;
        for (AbstractCard card : abstractPlayer.hand.group) {
            if (card instanceof DragonWeapon) {
                count++;
                addToBot(new ExhaustSpecificCardAction(card, abstractPlayer.hand));
            }
            if (card instanceof LavateinnDragon){
                hasLavatin = true;
            }
        }

        if (count > 1 && hasLavatin) {
            int toExhaust = 0;
            for (AbstractCard card : abstractPlayer.hand.group) {
                if (card instanceof DragonWeapon) {
                    toExhaust++;
                    if (toExhaust < 3){
                        addToBot(new ExhaustSpecificCardAction(card, abstractPlayer.hand));
                    }
                }
                if (card instanceof LavateinnDragon){
                    addToBot(new DiscardSpecificCardAction(card));
                }
            }
            AbstractCard a = new DualModeA();
            AbstractCard b = new DualModeB();
            AbstractCard c = new DualModeC();
            addToBot(new ChoiceAction(a, b, c));
        } else {
            addToBot(new DualRageAction(this.upgraded));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new DualRage();
    }
}

