package ru.sdevteam.vinv.game;


public interface IWireConnectable
{
	public boolean isCharged(); // проверяет, есть ли напряжение в линии
	public boolean isConsumer(); // является ли объект потребителем (true для башни)
	public boolean isConductor(); // является ли объект проводником (true для столба)
	public boolean isGenerator(); // является ли объект генератором (true для базы)

	public void onCircuitChanged();
}
