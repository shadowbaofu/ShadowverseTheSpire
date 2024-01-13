package shadowverse.cards.Neutral.Neutral;


import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.ChoiceAction;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.Amaterasu;
import shadowverse.cards.Neutral.Temp.Tsukuyomi;
import shadowverse.powers.NextTurnAmaterasu;
import shadowverse.powers.NextTurnTsukuyomi;

import java.util.ArrayList;

public class MoonAndSun
        extends AbstractNeutralCard {
    public static final String ID = "shadowverse:MoonAndSun";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:MoonAndSun");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/MoonAndSun.png";
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new Amaterasu());
        list.add(new Tsukuyomi());
        return list;
    }

    public MoonAndSun() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.NONE);
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
        AbstractCard[] list = new AbstractCard[returnChoice().size()];
        returnChoice().toArray(list);
        if (this.upgraded){
            for (AbstractCard c : list){
                c.upgrade();
            }
        }
        boolean has = abstractPlayer.hasPower(NextTurnAmaterasu.POWER_ID) || abstractPlayer.hasPower(NextTurnTsukuyomi.POWER_ID);
        if (has){
            addToBot(new GainEnergyAction(1));
            addToBot(new HealAction(abstractPlayer,abstractPlayer,1));
            addToBot(new MakeTempCardInHandAction(new EvolutionPoint()));
        }else {
            addToBot(new ChoiceAction(list));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new MoonAndSun();
    }
}

