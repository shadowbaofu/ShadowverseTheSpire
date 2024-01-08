package shadowverse.cards.Nemesis.Default;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.action.DrawPileToHandAction_Tag;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;

import java.util.ArrayList;
import java.util.List;


public class ServantOfDarkness
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:ServantOfDarkness";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ServantOfDarkness");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:ServantOfDarkness2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ServantOfDarkness.png";
    public static final String IMG_PATH2 = "img/cards/ServantOfDarkness2.png";
    private float rotationTimer;
    private int previewIndex;
    private boolean branch2;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new GuardFormGolem());
        list.add(new StrikeFormGolem());
        return list;
    }

    public ServantOfDarkness() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.ALL);
        this.baseBlock = 3;
    }

    public void update() {
        super.update();
        if (!branch2){
            if (this.hb.hovered)
                if (this.rotationTimer <= 0.0F) {
                    this.rotationTimer = 2.0F;
                    this.cardsToPreview =  returnChoice().get(previewIndex).makeCopy();
                    if (this.previewIndex == returnChoice().size() - 1) {
                        this.previewIndex = 0;
                    } else {
                        this.previewIndex++;
                    }
                } else {
                    this.rotationTimer -= Gdx.graphics.getDeltaTime();
                }
        }
    }

    public void upgrade() {
        ((UpgradeBranch)((BranchableUpgradeCard)this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot( new GainBlockAction(abstractPlayer, this.block));
        switch (chosenBranch()){
            case 0:
                addToBot(new SFXAction("ServantOfDarkness"));
                AbstractCard g = new GuardFormGolem();
                g.setCostForTurn(0);
                addToBot(new MakeTempCardInHandAction(g));
                if (this.upgraded){
                    AbstractCard s = new StrikeFormGolem();
                    s.setCostForTurn(0);
                    addToBot(new MakeTempCardInHandAction(s));
                }
                break;
            case 1:
                addToBot(new SFXAction("ServantOfDarkness2"));
                addToBot(new DrawPileToHandAction_Tag(2, AbstractShadowversePlayer.Enums.ARTIFACT,null));
                ArrayList<AbstractCard> list = new ArrayList<>();
                ArrayList<String> dup = new ArrayList<>();
                for (AbstractCard c: abstractPlayer.exhaustPile.group){
                    if (c.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT)&&!dup.contains(c.cardID)){
                        dup.add(c.cardID);
                        AbstractCard card = c.makeCopy();
                        list.add(card);
                    }
                }
                if (list.size()>=6){
                    addToBot(new MakeTempCardInHandAction(new EvolutionPoint()));
                    addToBot(new MakeTempCardInHandAction(new Miracle()));
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return  new ServantOfDarkness();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++ServantOfDarkness.this.timesUpgraded;
                ServantOfDarkness.this.upgraded = true;
                ServantOfDarkness.this.name = cardStrings.NAME + "+";
                ServantOfDarkness.this.initializeTitle();
                ServantOfDarkness.this.baseBlock = 4;
                ServantOfDarkness.this.upgradedBlock = true;
                ServantOfDarkness.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++ServantOfDarkness.this.timesUpgraded;
                ServantOfDarkness.this.upgraded = true;
                ServantOfDarkness.this.baseBlock = 6;
                ServantOfDarkness.this.upgradedBlock = true;
                ServantOfDarkness.this.upgradeBaseCost(1);
                ServantOfDarkness.this.tags.add(AbstractShadowversePlayer.Enums.ARTIFACT);
                ServantOfDarkness.this.textureImg = IMG_PATH2;
                ServantOfDarkness.this.loadCardImage(IMG_PATH2);
                ServantOfDarkness.this.name = cardStrings2.NAME;
                ServantOfDarkness.this.initializeTitle();
                ServantOfDarkness.this.rawDescription = cardStrings2.DESCRIPTION;
                ServantOfDarkness.this.initializeDescription();
                ServantOfDarkness.this.branch2 = true;
            }
        });

        return list;
    }
}

