package shadowverse.cards.Necromancer.Default;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractEnhanceCard;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.characters.Necromancer;

import java.util.ArrayList;

public class Zebet
        extends AbstractEnhanceCard {
    public static final String ID = "shadowverse:Zebet";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Zebet");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Zebet.png";
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new VelociousBeetle());
        list.add(new VirulentHornet());
        list.add(new ViciousScorpion());
        list.add(new VengefulMantis());
        list.add(new VeiledShudderfly());
        return list;
    }

    public Zebet() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.SKILL, Necromancer.Enums.COLOR_PURPLE, CardRarity.RARE, CardTarget.SELF, 3, 5);
        this.baseBlock = 12;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
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
    public void exEnhanceUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Zebet_Ex"));
        addToBot(new GainBlockAction(p,this.block));
        for (int i = 0; i < 4; i ++){
            AbstractCard c = returnChoice().get(AbstractDungeon.cardRng.random(4)).makeStatEquivalentCopy();
            if (this.upgraded){
                c.upgrade();
            }
            addToBot(new MakeTempCardInHandAction(c));
        }
    }

    @Override
    public void enhanceUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Zebet_Eh"));
        addToBot(new GainBlockAction(p,this.block));
        for (int i = 0; i < 2; i ++){
            AbstractCard c = returnChoice().get(AbstractDungeon.cardRng.random(4)).makeStatEquivalentCopy();
            if (this.upgraded){
                c.upgrade();
            }
            addToBot(new MakeTempCardInHandAction(c));
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Zebet"));
        addToBot(new GainBlockAction(p,this.block));
        AbstractCard c = returnChoice().get(AbstractDungeon.cardRng.random(4)).makeStatEquivalentCopy();
        if (this.upgraded){
            c.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(c));
    }


    public AbstractCard makeCopy() {
        return new Zebet();
    }
}


