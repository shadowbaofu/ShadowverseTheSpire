package shadowverse.cards.Witch.Academic;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.ChoiceAction;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.Amaterasu;
import shadowverse.cards.Neutral.Temp.Blackwyrm;
import shadowverse.cards.Neutral.Temp.Tsukuyomi;
import shadowverse.cards.Neutral.Temp.Whitewyrm;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;

import java.util.ArrayList;

public class AcademyWyrmist
        extends CustomCard {
    public static final String ID = "shadowverse:AcademyWyrmist";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:AcademyWyrmist");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/AcademyWyrmist.png";
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new Whitewyrm());
        list.add(new Blackwyrm());
        return list;
    }

    public AcademyWyrmist() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 6;
        this.tags.add(AbstractShadowversePlayer.Enums.MYSTERIA);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
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
        addToBot(new SFXAction("AcademyWyrmist"));
        addToBot(new GainBlockAction(abstractPlayer,this.block));
        if (abstractPlayer instanceof AbstractShadowversePlayer){
            ((AbstractShadowversePlayer)abstractPlayer).mysteriaCount++;
        }
        AbstractCard[] list = new AbstractCard[returnChoice().size()];
        returnChoice().toArray(list);
        if (this.upgraded){
            for (AbstractCard c : list){
                c.upgrade();
            }
        }
        addToBot(new ChoiceAction(list));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new AcademyWyrmist();
    }
}

