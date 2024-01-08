package shadowverse.cards.Neutral.Neutral;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.action.BlockPerCardAction;
import shadowverse.action.SatanaelAction;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.cards.Neutral.Temp.*;

import java.util.ArrayList;
import java.util.List;

public class Satan extends AbstractNeutralCard implements BranchableUpgradeCard {
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Satan");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Satanael");
    public static final String ID = "shadowverse:Satan";
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Satan.png";
    public static final String IMG_PATH2 = "img/cards/Satanael.png";

    public static ArrayList<AbstractCard> returnCocytusDeck() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new Flamelord());
        list.add(new Desire());
        list.add(new Scorpion());
        list.add(new HellBeast());
        list.add(new WrathfulIcefiend());
        list.add(new ViciousCommander());
        list.add(new DemonOfPurgatory());
        list.add(new Behemoth());
        list.add(new InfernalGaze());
        list.add(new InfernalSurge());
        list.add(new HeavenFall());
        list.add(new EarthFall());
        list.add(new Astaroth());
        return list;
    }

    public int baseCost;
    public int accCost;
    public CardType baseType;
    public CardType accType;
    public boolean played;


    public static AbstractCard returnCocytusCard(Random rng) {
        return returnCocytusDeck().get(rng.random(returnCocytusDeck().size() - 1));
    }

    public Satan() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
        this.accCost = 1;
        this.baseCost = cost;
        this.baseType = type;
        this.accType = CardType.SKILL;
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    @Override
    public void update() {
        if (chosenBranch() == 0) {
            if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                if (this.type == baseType) {
                    this.baseCost = this.costForTurn;
                } else {
                    if (this.cost <= this.accCost) {
                        this.baseCost = this.cost;
                    }
                }
                if (!played) {
                    if (EnergyPanel.getCurrentEnergy() < baseCost) {
                        setCostForTurn(accCost);
                        this.type = accType;
                    } else {
                        if (this.type == accType) {
                            setCostForTurn(baseCost);
                            this.type = baseType;
                        }
                    }
                }
            }else {
                resetAttributes();
                this.type = baseType;
            }
        }
        super.update();
    }

    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        played = false;
    }

    public void baseUse(AbstractPlayer p, AbstractMonster m){
        addToBot(new SFXAction("Satan"));
        for (AbstractCard c : p.drawPile.group) {
            addToBot(new ExhaustSpecificCardAction(c, p.drawPile));
        }
        ArrayList<AbstractCard> cocytusDeck = returnCocytusDeck();
        for (AbstractCard ca : cocytusDeck) {
            addToBot(new MakeTempCardInDrawPileAction(ca, 1, true, true));
        }
    }

    public void accUse(AbstractPlayer p, AbstractMonster m){
        addToBot(new SFXAction("Satan_Accelerate"));
        ArrayList<String> l = new ArrayList<String>();
        while (true) {
            AbstractCard c = returnCocytusCard(AbstractDungeon.cardRandomRng).makeCopy();
            if (!l.contains(c.cardID)) {
                l.add(c.cardID);
                addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
            }
            if (l.size() >= 4) {
                break;
            }
        }
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                if (this.type == accType && this.costForTurn == accCost) {
                    accUse(abstractPlayer, abstractMonster);
                } else {
                    baseUse(abstractPlayer, abstractMonster);
                }
                played = true;
                break;
            case 1:
                addToBot(new SFXAction("Satanael"));
                addToBot(new BlockPerCardAction(this.block));
                addToBot(new SatanaelAction());
                break;
        }
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Satan.this.timesUpgraded;
                Satan.this.upgraded = true;
                Satan.this.name = cardStrings.NAME + "+";
                Satan.this.initializeTitle();
                Satan.this.upgradeBaseCost(3);
                Satan.this.baseCost = 3;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Satan.this.timesUpgraded;
                Satan.this.upgraded = true;
                Satan.this.textureImg = IMG_PATH2;
                Satan.this.loadCardImage(IMG_PATH2);
                Satan.this.name = cardStrings2.NAME;
                Satan.this.initializeTitle();
                Satan.this.rawDescription = cardStrings2.DESCRIPTION;
                Satan.this.baseBlock = 3;
                Satan.this.upgradedBlock = true;
                Satan.this.upgradeBaseCost(2);
                Satan.this.initializeDescription();
            }
        });
        return list;
    }

    public AbstractCard makeCopy() {
        return new Satan();
    }

    public void triggerOnGlowCheck() {
        if (EnergyPanel.getCurrentEnergy() < baseCost && this.type == accType) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}

