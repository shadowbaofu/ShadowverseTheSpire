package shadowverse.cards.Neutral.Neutral;


import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.cards.Neutral.Temp.*;

import java.util.ArrayList;
import java.util.Arrays;

public class WingedInversion
        extends AbstractNeutralCard {
    public static final String ID = "shadowverse:WingedInversion";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:WingedInversion");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WingedInversion.png";
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new DarkAngelChoice());
        list.add(new AngelChoice());
        return list;
    }

    public static ArrayList<AbstractCard> returnChoice2() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new DarkAngelChoice());
        list.add(new AngelChoice());
        list.add(new FallenAngel());
        return list;
    }

    public WingedInversion() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.NONE);
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
                this.cardsToPreview = returnChoice2().get(previewIndex).makeCopy();
                if (this.previewIndex == returnChoice2().size() - 1) {
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
        ArrayList<AbstractCard> stanceChoices = returnChoice();
        if (this.upgraded){
            for (AbstractCard c : stanceChoices){
                c.upgrade();
            }
        }
        addToBot(new ChooseOneAction(stanceChoices));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new WingedInversion();
    }
}

