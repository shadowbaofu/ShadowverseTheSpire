package shadowverse.cards.Neutral.Neutral;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.purple.DevaForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.cards.Bishop.Default.Eris;
import shadowverse.cards.Dragon.Default.RowenCard;
import shadowverse.cards.Elf.Wood.Arisa;
import shadowverse.cards.Necromancer.Default.Luna;
import shadowverse.cards.Nemesis.Resonance.Yuwan;
import shadowverse.cards.Royal.Ambush.Erika;
import shadowverse.cards.Vampire.Default.Urias;
import shadowverse.cards.Witch.Mech.Isabelle;
import shadowverse.characters.*;


import java.util.ArrayList;


public class Elena
        extends AbstractNeutralCard {
    public static final String ID = "shadowverse:Elena";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Elena");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Elena.png";
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new Arisa());
        list.add(new Isabelle());
        list.add(new Erika());
        list.add(new Urias());
        list.add(new Luna());
        list.add(new RowenCard());
        list.add(new Eris());
        list.add(new Yuwan());
        return list;
    }

    public Elena() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 3;
        this.exhaust = true;
    }

    public void update() {
        super.update();
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

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Elena"));
        addToBot(new DamageAction(m,new DamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, false, card -> {
            return card.color != CardColor.COLORLESS && card.type != CardType.CURSE && card.type != CardType.STATUS;
        }, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        p.hand.moveToDiscardPile(c);
                        c.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);
                        if (c.color == Witchcraft.Enums.COLOR_BLUE){
                            AbstractCard toAdd = new Isabelle();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == Elf.Enums.COLOR_GREEN){
                            AbstractCard toAdd = new Arisa();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == Royal.Enums.COLOR_YELLOW){
                            if (p.maxOrbs < 5) {
                                int toIncrease = 5 - p.maxOrbs;
                                addToBot(new IncreaseMaxOrbAction(toIncrease));
                            }
                            AbstractCard toAdd = new Erika();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == Vampire.Enums.COLOR_SCARLET){
                            AbstractCard toAdd = new Urias();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == Necromancer.Enums.COLOR_PURPLE){
                            AbstractCard toAdd = new Luna();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == Dragon.Enums.COLOR_BROWN){
                            AbstractCard toAdd = new RowenCard();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == Bishop.Enums.COLOR_WHITE){
                            if (p.maxOrbs < 5) {
                                int toIncrease = 5 - p.maxOrbs;
                                addToBot(new IncreaseMaxOrbAction(toIncrease));
                            }
                            AbstractCard toAdd = new Eris();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == Nemesis.Enums.COLOR_SKY){
                            AbstractCard toAdd = new Yuwan();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == CardColor.RED){
                            AbstractCard toAdd = new DemonForm();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == CardColor.GREEN){
                            AbstractCard toAdd = new WraithForm();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == CardColor.BLUE){
                            AbstractCard toAdd = new EchoForm();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }else if (c.color == CardColor.PURPLE){
                            AbstractCard toAdd = new DevaForm();
                            if (Elena.this.upgraded)
                                toAdd.upgrade();
                            addToBot(new MakeTempCardInHandAction(toAdd));
                        }
                        this.isDone = true;
                    }
                });
            }
        }));
        if (this.upgraded && AbstractDungeon.actionManager.turn >= 5){
            addToBot(new DamageAction(m,new DamageInfo(p, 50, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.BLUE), 0.8F));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Elena();
    }
}

