package com.boatcraft.builder.entity;

import com.boatcraft.builder.BoatCraftMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BuildBlockEntity extends Entity {
    private static final TrackedData<String> BLOCK_ID = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Float> SCALE_X = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> SCALE_Y = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> SCALE_Z = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROT_X = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROT_Y = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROT_Z = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> ANCHORED = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> ALPHA = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> SCALE_AXIS = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> ROT_AXIS = DataTracker.registerData(BuildBlockEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public BuildBlockEntity(EntityType<? extends BuildBlockEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(BLOCK_ID, "minecraft:stone");
        dataTracker.startTracking(SCALE_X, 1.0f);
        dataTracker.startTracking(SCALE_Y, 1.0f);
        dataTracker.startTracking(SCALE_Z, 1.0f);
        dataTracker.startTracking(ROT_X, 0.0f);
        dataTracker.startTracking(ROT_Y, 0.0f);
        dataTracker.startTracking(ROT_Z, 0.0f);
        dataTracker.startTracking(ANCHORED, true);
        dataTracker.startTracking(ALPHA, 1.0f);
        dataTracker.startTracking(SCALE_AXIS, 0);
        dataTracker.startTracking(ROT_AXIS, 1);
    }

    @Override
    public void tick() {
        super.tick();
        if (!isAnchored()) {
            Vec3d v = getVelocity().multiply(0.985, 0.985, 0.985).add(0, -0.045, 0);
            setVelocity(v);
            move(MovementType.SELF, v);
            if (isOnGround()) {
                setVelocity(new Vec3d(v.x * 0.72, Math.abs(v.y) < 0.11 ? 0 : v.y * -0.2, v.z * 0.72));
            }
        } else {
            setVelocity(Vec3d.ZERO);
        }
    }

    public void setBlockId(String id) { dataTracker.set(BLOCK_ID, id); }
    public String getBlockId() { return dataTracker.get(BLOCK_ID); }

    public void setScale(float x, float y, float z) {
        dataTracker.set(SCALE_X, MathHelper.clamp(x, 1.0f / 16.0f, 16.0f));
        dataTracker.set(SCALE_Y, MathHelper.clamp(y, 1.0f / 16.0f, 16.0f));
        dataTracker.set(SCALE_Z, MathHelper.clamp(z, 1.0f / 16.0f, 16.0f));
    }
    public float getScaleX() { return dataTracker.get(SCALE_X); }
    public float getScaleY() { return dataTracker.get(SCALE_Y); }
    public float getScaleZ() { return dataTracker.get(SCALE_Z); }
    public int getScaleAxis() { return dataTracker.get(SCALE_AXIS); }
    public void cycleScaleAxis() { dataTracker.set(SCALE_AXIS, (getScaleAxis() + 1) % 3); }
    public void adjustScale(int axis, float amount) {
        float x = getScaleX(), y = getScaleY(), z = getScaleZ();
        if (axis == 0) x += amount;
        if (axis == 1) y += amount;
        if (axis == 2) z += amount;
        setScale(x, y, z);
    }

    public void setRotation(float x, float y, float z) {
        dataTracker.set(ROT_X, MathHelper.wrapDegrees(x));
        dataTracker.set(ROT_Y, MathHelper.wrapDegrees(y));
        dataTracker.set(ROT_Z, MathHelper.wrapDegrees(z));
    }
    public float getRotX() { return dataTracker.get(ROT_X); }
    public float getRotY() { return dataTracker.get(ROT_Y); }
    public float getRotZ() { return dataTracker.get(ROT_Z); }
    public int getRotAxis() { return dataTracker.get(ROT_AXIS); }
    public void cycleRotationAxis() { dataTracker.set(ROT_AXIS, (getRotAxis() + 1) % 3); }
    public void adjustRotation(int axis, float amount) {
        float x = getRotX(), y = getRotY(), z = getRotZ();
        if (axis == 0) x += amount;
        if (axis == 1) y += amount;
        if (axis == 2) z += amount;
        setRotation(x, y, z);
    }

    public boolean isAnchored() { return dataTracker.get(ANCHORED); }
    public void setAnchored(boolean anchored) { dataTracker.set(ANCHORED, anchored); }
    public float getAlpha() { return dataTracker.get(ALPHA); }
    public void setAlpha(float alpha) { dataTracker.set(ALPHA, MathHelper.clamp(alpha, 0.10f, 1.0f)); }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        setBlockId(nbt.getString("BlockId"));
        setScale(nbt.getFloat("ScaleX"), nbt.getFloat("ScaleY"), nbt.getFloat("ScaleZ"));
        setRotation(nbt.getFloat("RotX"), nbt.getFloat("RotY"), nbt.getFloat("RotZ"));
        setAnchored(nbt.getBoolean("Anchored"));
        setAlpha(nbt.contains("Alpha") ? nbt.getFloat("Alpha") : 1.0f);
        dataTracker.set(SCALE_AXIS, MathHelper.clamp(nbt.getInt("ScaleAxis"), 0, 2));
        dataTracker.set(ROT_AXIS, MathHelper.clamp(nbt.getInt("RotAxis"), 0, 2));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("BlockId", getBlockId());
        nbt.putFloat("ScaleX", getScaleX());
        nbt.putFloat("ScaleY", getScaleY());
        nbt.putFloat("ScaleZ", getScaleZ());
        nbt.putFloat("RotX", getRotX());
        nbt.putFloat("RotY", getRotY());
        nbt.putFloat("RotZ", getRotZ());
        nbt.putBoolean("Anchored", isAnchored());
        nbt.putFloat("Alpha", getAlpha());
        nbt.putInt("ScaleAxis", getScaleAxis());
        nbt.putInt("RotAxis", getRotAxis());
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override public boolean isAttackable() { return true; }
    @Override public boolean canHit() { return true; }
    @Override public boolean doesRenderOnFire() { return false; }
}
