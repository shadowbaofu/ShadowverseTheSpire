package shadowverse.cards.Neutral.Neutral;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.Shadowverse;
import shadowverse.action.TechnolordAction;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.ArrayList;
import java.util.List;


public class Technolord
        extends AbstractNeutralCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Technolord";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Technolord");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Technolord2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Technolord.png";
    public int baseCost;
    public int accCost;
    public CardType baseType;
    public CardType accType;
    public boolean played;
    private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/Technolord_L.png");

    public Technolord() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 2;
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
        this.accCost = 1;
        this.baseCost = cost;
        this.baseType = type;
        this.accType = CardType.SKILL;
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE) && c.type != CardType.SKILL)
                count++;
        }
        this.rawDescription = chosenBranch() == 0 ? cardStrings.DESCRIPTION : cardStrings2.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void update() {
        if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                Shadowverse.Accelerate(this)) {
            setCostForTurn(chosenBranch() == 0 ? 1 : 0);
            this.type = CardType.SKILL;
        } else {
            if (this.type == CardType.SKILL) {
                setCostForTurn(chosenBranch() == 0 ? 3 : 4);
                this.type = CardType.ATTACK;
            }
        }
        super.update();
    }

    public void triggerOnGlowCheck() {
        if (EnergyPanel.getCurrentEnergy() < baseCost && this.type == accType) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (chosenBranch()) {
            case 0:
                if (this.type == accType && this.costForTurn == accCost) {
                    accUse(p, m);
                } else {
                    baseUse(p, m);
                }
                played = true;
                break;
            case 1:
                if (this.type == accType && this.costForTurn == accCost) {
                    accUse2(p, m);
                } else {
                    baseUse2(p, m);
                }
                played = true;
                break;
        }
    }

    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TechnolordAction());
    }

    public void baseUse2(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE)) {
                count++;
            }
        }
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.magicNumber * count, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.LIGHTNING, true));
    }

    public void accUse2(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new DrawCardAction(1));
    }

    public void accUse(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE) && c.type != CardType.SKILL)
                count++;
        }
        this.magicNumber = count;
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.LIGHTNING));
        }
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        played = false;
    }

    public AbstractCard makeCopy() {
        return new Technolord();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Technolord.this.timesUpgraded;
                Technolord.this.upgraded = true;
                Technolord.this.name = cardStrings.NAME + "+";
                Technolord.this.initializeTitle();
                Technolord.this.baseDamage = 3;
                Technolord.this.upgradedDamage = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Technolord.this.timesUpgraded;
                Technolord.this.upgraded = true;
                Technolord.this.name = cardStrings2.NAME;
                Technolord.this.initializeTitle();
                Technolord.this.rawDescription = cardStrings2.DESCRIPTION;
                Technolord.this.initializeDescription();
                Technolord.this.upgradeBaseCost(4);
                Technolord.this.accCost = 0;
                Technolord.this.baseCost = 4;
                Technolord.this.baseMagicNumber = 7;
                Technolord.this.magicNumber = Technolord.this.baseMagicNumber;
                Technolord.this.upgradedMagicNumber = true;
                Technolord.this.baseDamage = 6;
                Technolord.this.upgradedDamage = true;
                Technolord.this.target = CardTarget.ENEMY;
            }
        });
        return list;
    }
}


