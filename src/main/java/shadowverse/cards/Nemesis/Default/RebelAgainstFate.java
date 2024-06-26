package shadowverse.cards.Nemesis.Default;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.characters.Nemesis;
import shadowverse.stance.Resonance;

import java.util.ArrayList;
import java.util.List;


public class RebelAgainstFate
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:RebelAgainstFate";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:RebelAgainstFate");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:RebelAgainstFate2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/RebelAgainstFate.png";
    private float rotationTimer;
    private int previewIndex;
    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new AncientArtifact());
        list.add(new MysticArtifact());
        list.add(new TisiphoneCard());
        list.add(new AlectorCard());
        list.add(new MegaeraCard());
        return list;
    }

    public static ArrayList<AbstractCard> returnChoice2() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new YuwanFervor());
        list.add(new BelphometAuthority());
        return list;
    }

    public static ArrayList<AbstractCard> returnElinese(){
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new TisiphoneCard());
        list.add(new AlectorCard());
        list.add(new MegaeraCard());
        return list;
    }
    public static AbstractCard returnRandomElinese(Random rng){
        return returnElinese().get(rng.random(returnElinese().size() - 1));
    }

    public RebelAgainstFate() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.SKILL, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
    }

    public void update() {
        super.update();
        if (this.hb.hovered)
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = 2.0F;
                if (chosenBranch() == 0){
                    this.cardsToPreview = returnChoice().get(previewIndex).makeCopy();
                }else {
                    this.cardsToPreview = returnChoice2().get(previewIndex).makeCopy();
                }
                if (this.previewIndex == (chosenBranch() == 0?returnChoice().size() - 1:returnChoice2().size() - 1)) {
                    this.previewIndex = 0;
                } else {
                    this.previewIndex++;
                }
            } else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
    }

    public void upgrade() {
        ((UpgradeBranch)((BranchableUpgradeCard)this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
                stanceChoices.add(new YuwanFury());
                stanceChoices.add(new BelphometCrackdown());
                if (abstractPlayer.stance.ID.equals(Resonance.STANCE_ID)){
                    addToBot(new SFXAction("RebelAgainstFate"));
                    AbstractCard a = new AncientArtifact();
                    AbstractCard m = new MysticArtifact();
                    m.setCostForTurn(0);
                    addToBot(new MakeTempCardInHandAction(a));
                    addToBot(new MakeTempCardInHandAction(m));
                    AbstractCard c = returnRandomElinese(AbstractDungeon.cardRandomRng).makeStatEquivalentCopy();
                    c.retain= true;
                    c.selfRetain = true;
                    c.initializeDescription();
                    c.applyPowers();
                    abstractPlayer.hand.addToTop(c);
                }else {
                    addToBot(new ChooseOneAction(stanceChoices));
                }
                break;
            case 1:
                ArrayList<AbstractCard> stanceChoices2 = new ArrayList<>();
                stanceChoices2.add(new YuwanFervor());
                stanceChoices2.add(new BelphometAuthority());
                addToBot(new ChooseOneAction(stanceChoices2));
                break;
        }
    }


    public AbstractCard makeCopy() {
        return  new RebelAgainstFate();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++RebelAgainstFate.this.timesUpgraded;
                RebelAgainstFate.this.upgraded = true;
                RebelAgainstFate.this.name = cardStrings.NAME + "+";
                RebelAgainstFate.this.initializeTitle();
                RebelAgainstFate.this.upgradeBaseCost(1);
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++RebelAgainstFate.this.timesUpgraded;
                RebelAgainstFate.this.upgraded = true;
                RebelAgainstFate.this.name = cardStrings2.NAME;
                RebelAgainstFate.this.initializeTitle();
                RebelAgainstFate.this.rawDescription = cardStrings2.DESCRIPTION;
                RebelAgainstFate.this.initializeDescription();
                RebelAgainstFate.this.upgradeBaseCost(1);
            }
        });
        return list;
    }
}

