package shadowverse.cards.Necromancer.Mech;


import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cardmods.AeneaMod;
import shadowverse.cards.Neutral.Temp.GenerateNine;
import shadowverse.cards.Neutral.Temp.JoySecured;
import shadowverse.cards.Neutral.Temp.Manmaru1;
import shadowverse.cards.Neutral.Temp.ManmaruRe;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Elf;
import shadowverse.characters.Necromancer;
import shadowverse.powers.AeneaPower;

import java.util.ArrayList;
import java.util.List;


public class Aenea
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Aenea";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Aenea");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Aenea2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Aenea.png";
    private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/Aenea_L.png");
    private float rotationTimer;
    private int previewIndex;
    private int branch;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new Manmaru1());
        list.add(new GenerateNine());
        return list;
    }

    public static ArrayList<AbstractCard> returnChoice2() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new ManmaruRe());
        list.add(new JoySecured());
        return list;
    }

    public Aenea() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.POWER, Necromancer.Enums.COLOR_PURPLE, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    public void update() {
        super.update();
        if (branch == 0) {
            if (this.hb.hovered)
                if (this.rotationTimer <= 0.0F) {
                    this.rotationTimer = 2.0F;
                    this.cardsToPreview = (AbstractCard) returnChoice().get(previewIndex).makeCopy();
                    if (this.previewIndex == returnChoice().size() - 1) {
                        this.previewIndex = 0;
                    } else {
                        this.previewIndex++;
                    }
                } else {
                    this.rotationTimer -= Gdx.graphics.getDeltaTime();
                }
        } else {
            if (this.hb.hovered)
                if (this.rotationTimer <= 0.0F) {
                    this.rotationTimer = 2.0F;
                    this.cardsToPreview = (AbstractCard) returnChoice2().get(previewIndex).makeCopy();
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

    public void applyPowers() {
        super.applyPowers();
        if (branch == 1) {
            int count = 0;
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
                if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE)) {
                    count++;
                }
            }
            this.rawDescription = cardStrings2.DESCRIPTION;
            this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[0] + count;
            this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                AbstractCard card = (AbstractCard) new Manmaru1().makeStatEquivalentCopy();
                card.setCostForTurn(0);
                if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false)))
                    addToBot(new SFXAction("Aenea_L"));
                else
                    addToBot(new SFXAction("Aenea"));
                addToBot(new MakeTempCardInHandAction(card));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new EnergyDownPower(abstractPlayer, 1, true), 1));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new AeneaPower(abstractPlayer, this.magicNumber), this.magicNumber));
                break;
            case 1:
                addToBot(new SFXAction("Aenea2"));
                addToBot(new GainBlockAction(abstractPlayer, this.block));
                addToBot(new MakeTempCardInHandAction(new ManmaruRe()));
                int count = 0;
                for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
                    if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE)) {
                        count++;
                    }
                }
                if (count > 10) {
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            this.isDone = true;
                            for (AbstractCard c : abstractPlayer.hand.group) {
                                if (c.type == CardType.ATTACK && c.hasTag(AbstractShadowversePlayer.Enums.MACHINE) && c != Aenea.this) {
                                    CardModifierManager.addModifier(c, (AbstractCardModifier) new AeneaMod());
                                    c.superFlash();
                                }
                            }
                        }
                    });
                    addToBot(new MakeTempCardInHandAction(new JoySecured()));
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Aenea();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Aenea.this.timesUpgraded;
                Aenea.this.upgraded = true;
                Aenea.this.name = cardStrings.NAME + "+";
                Aenea.this.initializeTitle();
                Aenea.this.upgradeBaseCost(1);
                Aenea.this.branch = 0;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Aenea.this.timesUpgraded;
                Aenea.this.upgraded = true;
                Aenea.this.name = cardStrings2.NAME;
                Aenea.this.initializeTitle();
                Aenea.this.rawDescription = cardStrings2.DESCRIPTION;
                Aenea.this.initializeDescription();
                Aenea.this.upgradeBaseCost(1);
                Aenea.this.type = CardType.ATTACK;
                Aenea.this.baseBlock = 4;
                Aenea.this.upgradedBlock = true;
                Aenea.this.branch = 1;
            }
        });
        return list;
    }
}


