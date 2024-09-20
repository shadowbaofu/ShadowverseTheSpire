package shadowverse.cards.Royal.Evolve;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Royal;
import shadowverse.relics.KagemitsuSword;

public class Troya extends CustomCard {
    public static final String ID = "shadowverse:Troya";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Troya.png";
    public static final String IMG_PATH_EV = "img/cards/Troya_Ev.png";
    private int count;

    public Troya() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.count = 0;
        this.tags.add(AbstractShadowversePlayer.Enums.EVOLVEABLE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.textureImg = IMG_PATH_EV;
            this.loadCardImage(IMG_PATH_EV);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void degrade() {
        if (this.upgraded) {
            degradeName();
            this.textureImg = IMG_PATH;
            this.loadCardImage(IMG_PATH);
            this.rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
            this.superFlash();
            this.applyPowers();
        }
    }

    public void degradeName() {
        --this.timesUpgraded;
        this.upgraded = false;
        this.name = NAME;
        this.initializeTitle();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new SFXAction(ID.replace("shadowverse:", "") + "_Ev"));
        } else {
            addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() >= 2) {
            addToBot(new DrawCardAction(1));
        }
        if (this.upgraded) {
            this.addToTop(new MakeTempCardInHandAction(new EvolutionPoint(), 1));
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false), 1));
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false), 1));
            this.count = 0;
            this.degrade();
            if (p.hasRelic(KagemitsuSword.ID) || p.hasPower("shadowverse:SeofonPower")) {
                this.upgrade();
            }
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (!this.upgraded && c.type == CardType.ATTACK && (c.upgraded || c.hasTag(AbstractShadowversePlayer.Enums.EVOLVEABLE)) && AbstractDungeon.player.hand.group.contains(this)) {
            this.count++;
            if (this.count >= 2) {
                this.superFlash();
                this.upgrade();
                addToBot(new SFXAction(ID.replace("shadowverse:", "") + "_Ev"));
            }
        }
    }

    @Override
    public void triggerAtStartOfTurn() {
        super.triggerAtStartOfTurn();
        this.count = 0;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() >= 2) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Troya();
    }
}

