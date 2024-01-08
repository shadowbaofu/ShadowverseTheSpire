package shadowverse.cards.Witch.Academic;

import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.action.ChoiceAction2;
import shadowverse.action.DrawPileToHandAction_Tag_NOREPEAT;
import shadowverse.cardmods.HannaMod;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.relics.AnneBOSS;

import java.util.ArrayList;
import java.util.List;

public class Hanna extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Hanna";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Hanna");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Hanna2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Hanna.png";
    public static final String IMG_PATH2 = "img/cards/Hanna2.png";
    private boolean branch1 = true;
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new MysterianCircle());
        list.add(new MysterianMissile());
        list.add(new MysterianRite());
        return list;
    }

    public Hanna() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 5;
        this.tags.add(AbstractShadowversePlayer.Enums.MYSTERIA);
    }

    public void update() {
        super.update();
        if (branch1){
            if (this.hb.hovered)
                if (this.rotationTimer <= 0.0F) {
                    this.rotationTimer = 2.0F;
                    this.cardsToPreview = (AbstractCard) returnChoice().get(previewIndex).makeCopy();
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
    }

    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer && !branch1) {
            AbstractShadowversePlayer w = (AbstractShadowversePlayer) AbstractDungeon.player;
            int count = w.mysteriaCount;
            this.rawDescription = cardStrings2.DESCRIPTION;
            this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[0] + count;
            this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer) {
            ((AbstractShadowversePlayer) AbstractDungeon.player).mysteriaCount++;
        }
        switch (chosenBranch()) {
            case 0:
                addToBot((AbstractGameAction) new SFXAction("Hanna"));
                addToBot((AbstractGameAction) new GainBlockAction((AbstractCreature) abstractPlayer, (AbstractCreature) abstractPlayer, this.block));
                AbstractCard a = new MysterianCircle();
                AbstractCard b = new MysterianMissile();
                AbstractCard c = new MysterianRite();
                if (this.upgraded){
                    a.upgrade();
                    b.upgrade();
                    c.upgrade();
                }
                addToBot((AbstractGameAction)new ChoiceAction2(new AbstractCard[] { a, b, c }));
                addToBot((AbstractGameAction) new DrawPileToHandAction_Tag_NOREPEAT(1, AbstractShadowversePlayer.Enums.MYSTERIA, null, this));
                break;
            case 1:
                addToBot((AbstractGameAction) new SFXAction("Hanna2"));
                addToBot((AbstractGameAction) new GainBlockAction((AbstractCreature) abstractPlayer, (AbstractCreature) abstractPlayer, this.block));
                addToBot(new FetchAction(abstractPlayer.drawPile, card -> card.hasTag(AbstractShadowversePlayer.Enums.MYSTERIA) && card.type == CardType.SKILL, 1));
                if (((AbstractShadowversePlayer) AbstractDungeon.player).mysteriaCount>10){
                    for (AbstractCard card: abstractPlayer.hand.group){
                        if (card.type == CardType.SKILL && (abstractPlayer.hasRelic(AnneBOSS.ID) || card.hasTag(AbstractShadowversePlayer.Enums.MYSTERIA))){
                            addToBot(new ReduceCostForTurnAction(card,1));
                            CardModifierManager.addModifier(card,new HannaMod());
                        }
                    }
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Hanna();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                Hanna.this.timesUpgraded = 1;
                Hanna.this.upgraded = true;
                Hanna.this.name = cardStrings.NAME + "+";
                Hanna.this.initializeTitle();
                Hanna.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                Hanna.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Hanna.this.timesUpgraded;
                Hanna.this.upgraded = true;
                Hanna.this.textureImg = IMG_PATH2;
                Hanna.this.loadCardImage(IMG_PATH2);
                Hanna.this.name = cardStrings2.NAME;
                Hanna.this.baseBlock = 9;
                Hanna.this.upgradedBlock = true;
                Hanna.this.rarity = CardRarity.UNCOMMON;
                Hanna.this.setDisplayRarity(Hanna.this.rarity);
                Hanna.this.initializeTitle();
                Hanna.this.rawDescription = cardStrings2.DESCRIPTION;
                Hanna.this.initializeDescription();
                Hanna.this.branch1 = false;
            }
        });
        return list;
    }
}

