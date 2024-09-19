package shadowverse.cards.Neutral.Neutral;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Dragon.Armed.Rola;
import shadowverse.cards.Dragon.Default.Aiela;
import shadowverse.cards.Dragon.Default.Razia;
import shadowverse.cards.Dragon.Discard1.DragoonMedic;
import shadowverse.cards.Dragon.Tempo.Forte;
import shadowverse.cards.Elf.Default.BenevolentElf;
import shadowverse.cards.Elf.Default.Lisa;
import shadowverse.cards.Elf.Long.Alberta;
import shadowverse.cards.Elf.Long.ElfGuard;
import shadowverse.cards.Royal.Default.DualbladeKnight;
import shadowverse.cards.Royal.Evolve.Charlotta;
import shadowverse.cards.Royal.Rally.WhitePaladin;

import java.util.ArrayList;

public class Goblin extends CustomCard {
    public static final String ID = "shadowverse:Goblin";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Goblin.png";
    private static final String TEXT = CardCrawlGame.languagePack.getUIString("shadowverse:Exhaust").TEXT[0];
    private boolean triggered;


    public Goblin() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 4;
        triggered = false;
    }

    public static ArrayList<AbstractCard> returnProphecy() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new WhitePaladin());
        list.add(new DualbladeKnight());
        list.add(new Charlotta());
        list.add(new Rola());
        list.add(new DragoonMedic());
        list.add(new Aiela());
        list.add(new Forte());
        list.add(new ElfGuard());
        list.add(new Lisa());
        list.add(new Alberta());
        list.add(new BenevolentElf());
        list.add(new Razia());
        list.add(new Razia());
        list.add(new Razia());
        list.add(new Razia());
        return list;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (this.upgraded && !triggered) {
            ArrayList<AbstractCard> list = returnProphecy();
            int r = AbstractDungeon.cardRandomRng.random(list.size() - 1);
            AbstractCard c = list.get(r);
            if (!c.exhaust) {
                c.exhaust = true;
                c.rawDescription += " NL " + TEXT + " 。";
                c.initializeDescription();
                c.applyPowers();
            }
            abstractPlayer.hand.addToTop(c);
            triggered = true;
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new Goblin();
    }
}

